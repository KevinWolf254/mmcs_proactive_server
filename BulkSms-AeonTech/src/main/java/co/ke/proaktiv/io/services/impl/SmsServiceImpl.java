package co.ke.proaktiv.io.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.external.api.AfricasTalkingGateway;
import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.ClientAdmin;
import co.ke.proaktiv.io.models.Cost;
import co.ke.proaktiv.io.models.Credit;
import co.ke.proaktiv.io.models.Inventory;
import co.ke.proaktiv.io.models.ShortCode;
import co.ke.proaktiv.io.pojos.EmailMessage;
import co.ke.proaktiv.io.pojos.PhoneNos;
import co.ke.proaktiv.io.pojos.Sms;
import co.ke.proaktiv.io.pojos.helpers.CostType;
import co.ke.proaktiv.io.pojos.helpers.Country;
import co.ke.proaktiv.io.pojos.helpers.Currency;
import co.ke.proaktiv.io.pojos.reports.Report;
import co.ke.proaktiv.io.pojos.reports.SmsDeliveryReport;
import co.ke.proaktiv.io.pojos.reports.SmsValidationReport;
import co.ke.proaktiv.io.services.ATChargesService;
import co.ke.proaktiv.io.services.ClientAdminService;
import co.ke.proaktiv.io.services.CostService;
import co.ke.proaktiv.io.services.CreditService;
import co.ke.proaktiv.io.services.EmailService;
import co.ke.proaktiv.io.services.ExchangeRatesService;
import co.ke.proaktiv.io.services.InventoryService;
import co.ke.proaktiv.io.services.ProChargesService;
import co.ke.proaktiv.io.services.ShortCodeService;
import co.ke.proaktiv.io.services.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private ClientAdminService adminService;
	@Autowired
	private CostService costService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ShortCodeService shortCodeService;
	@Autowired
	private ProChargesService proChargesService;
	@Autowired
	private ATChargesService aTChargesService;
	@Autowired
	private ExchangeRatesService ratesService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private CreditService creditService;
	
	@Value("${support.email}")
	private String email;
	
	@Value("${mmcs.aeon.at_username}")
	private String username;
	@Value("${mmcs.aeon.at_apikey}")
	private String apiKey;
	
	@Value("${mmcs.aeon.sender_id}")
	private String default_shortCode;
	
	private AfricasTalkingGateway gateway;

	@Override
	public SmsDeliveryReport sendSms(final Sms sms) {
		
		gateway = new AfricasTalkingGateway(username, apiKey);	        
		//check if client paid for sender_id
        final ClientAdmin admin = adminService.findByEmail(sms.getSender()).get();
        final Client client = admin.getClient();
        final Country country = client.getCountry();
        final Currency currency = ratesService.getCurrency(country);
		final ShortCode shortCode = shortCodeService.findByClient(client);
		final Credit credit;
        //calculate the cost to the client
        double client_cost = proChargesService.getProCharges((List<PhoneNos>) sms.getPhoneNosTotals(), country); 
        if(sms.getMessage().length() > 160)
        	client_cost = client_cost * 2;

        //validate sms_
        final Report report = validate(sms, client, client_cost);
        if(report.getCode() == 400) 
        	return new SmsDeliveryReport(400, report.getTitle(), report.getMessage(), "", 0, 0, "");
        
		//set the shortCode 
		final String shortCode_;			
		if(shortCode.isPaid()) 
			shortCode_ = shortCode.getName();
		else
			shortCode_ = "";
		final String messageId;
        int received = 0;
        int rejected = 0;
        final StringBuilder rejectedNos = new StringBuilder();
		try {
			//send sms_ to gateway and receive a response
            final JSONArray results = gateway.sendMessage(sms.getRecipients(),
            		sms.getMessage(), shortCode_);
            messageId = results.getJSONObject(0).getString("messageId");            

            final BigDecimal cost_amount = BigDecimal.ZERO; 
            
            for( int i = 0; i < results.length(); ++i ) {
                final JSONObject result = results.getJSONObject(i);

          		final String sCost = result.getString("cost");
        		final double amount = Double.parseDouble(sCost.substring(4).trim());
          		
        		if(result.getString("status").equals("Success")) {
        			received = received++;
        		}else {
        			rejectedNos.append(result.getString("number"));
        			rejected = rejected++;
        		}   
        		cost_amount.add(BigDecimal.valueOf(amount));
            }  
            cost_amount.setScale(2, RoundingMode.HALF_EVEN);
            costService.save(new Cost(CostType.SMS, Currency.KES, cost_amount.doubleValue(), 
            		messageId, client));
            
            //deduct client's credit
//            credit = creditService.subtract(client, client_cost);
        	
            //deduct inventory 
    		final Inventory inventory = inventoryService.findByCurrency(currency);
    		final BigDecimal inventory_ = BigDecimal.valueOf(inventory.getAmount());
    		inventory_.subtract(BigDecimal.valueOf(client_cost));
    		inventory_.setScale(2, RoundingMode.HALF_EVEN);
    		inventory.setAmount(inventory_.doubleValue());
    		inventoryService.save(inventory);
    		
        }catch (Exception e) {
        	log.info("***** Encountered an error while sending. Error: "+e.getMessage());
        	emailService.sendEmail(new EmailMessage(admin.getEmail(), "SMS Sending Error", 
        			"The following error occured "+e.getMessage()));
        	
    		return new SmsDeliveryReport(400, "Exception Error", e.getMessage(), "", 0, 0, "");
        }

		emailService.sendEmail(new EmailMessage(admin.getEmail(), "SMS Sent Successfully", 
				"Sms's were sent successfully. The credit remaining in your account is:"));
//						+credit.toString()));
		
		return new SmsDeliveryReport(200, "Success", "Sms was sent successfully", messageId, 
				received, rejected, rejectedNos.toString());
	}

	private Report validate(final Sms sms, final Client client, final double client_cost) {

        final Country country = client.getCountry();
        final Currency currency = ratesService.getCurrency(country);
        
		//check if client has enough credit
		final Credit credit = creditService.findByClient(client);
		if(credit.getAmount() < client_cost) {
            	return new Report(400, "Credit Insufficient", "Kindly top up credit");
		}
		//check if there is enough inventory
		final Inventory inventory = inventoryService.findByCurrency(currency);
		if(inventory.getAmount() < client_cost) {
			final double topUp = aTChargesService.getATCharges((List<PhoneNos>) sms.getPhoneNosTotals(), 
					country); 
			final BigDecimal kes_topUp = ratesService.convert(currency, Currency.KES,
					topUp);
			emailService.sendEmail(new EmailMessage(email, "SMS Sending Error", 
					"Not enough credit to send: "+currency+" "+client_cost+". Top up amount KES "
					+kes_topUp.setScale(2, RoundingMode.HALF_EVEN)));
			
        		return new Report(400, "Warning", "Kindly wait for 5 to 10 minutes"
        				+ "and try again");
		}
		return new Report(200, "Success", "");
	}

	@Override
	public SmsValidationReport validateSms(Sms sms) {

        final ClientAdmin admin = adminService.findByEmail(sms.getSender()).get();
        final Client client = admin.getClient();
        final Country country = client.getCountry();
        //calculate the cost to the client
        double client_cost = proChargesService.getProCharges((List<PhoneNos>) sms.getPhoneNosTotals(), country); 
        if(sms.getMessage().length() > 160)
        	client_cost = client_cost * 2;

        //validate sms_
        final Report report = validate(sms, client, client_cost);
        if(report.getCode() == 400) 
        	return new SmsValidationReport(400, "failed", "Cannot send sms", Boolean.FALSE);
        
        return new SmsValidationReport(200, "success", "Can send sms", Boolean.TRUE);
	}
	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
}
