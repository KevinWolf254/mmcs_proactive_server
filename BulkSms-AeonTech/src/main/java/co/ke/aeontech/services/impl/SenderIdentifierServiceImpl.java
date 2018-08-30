package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.ke.aeontech.external.api.AfricasTalkingGateway;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.SenderIdentifier;
import co.ke.aeontech.models.TransactionCost;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.Paid;
import co.ke.aeontech.pojos.RecipientCost;
import co.ke.aeontech.pojos.SmsInfo;
import co.ke.aeontech.repository.SenderIdentifierRepository;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.SenderIdentifierService;
import co.ke.aeontech.services.TransactionCostService;

@Service
public class SenderIdentifierServiceImpl implements SenderIdentifierService{

	@Autowired
	private SenderIdentifierRepository repository;
	@Autowired
	private OrganisationService orgService;
	@Autowired
	private TransactionCostService transService;
	@Autowired
	private EmailService emailService;
	
	@Value("${support.email}")
	private String email;
	
	private AfricasTalkingGateway gateway;
	
	@Value("${mmcs.aeon.at_username}")
	private String username;
	@Value("${mmcs.aeon.at_apikey}")
	private String apiKey;
	
	@Value("${mmcs.aeon.sender_id}")
	private String aeon_sender_id;
	
	@Override
	public void save(final SenderIdentifier senderId) {
		repository.save(senderId);		
	}

	@Override
	public SenderIdentifier findByOrganisationId(final Long id) {
		return repository.findByOrganisationId(id);
	}

	@Override
	public Boolean foundSenderIdByName(final String senderId) {
		if(findByName(senderId) == null)
			return false;
		return true;
	}

	@Override
	public SenderIdentifier findByName(final String senderId) {
		return repository.findByName(senderId);
	}

	@Override
	public OnSendDeliveryReport sendSms(final SmsInfo smsInfo) {
		gateway = new AfricasTalkingGateway(username, apiKey);
		//to save the costs of sending the message
        final List<RecipientCost> sent_list = new ArrayList<>();
        final List<RecipientCost> rejected_list = new ArrayList<>();
        //to save the phone numbers that received the message or were rejected
//        final List<String> sent_phones = new ArrayList<>();
//        final List<String> rejected_phones = new ArrayList<>();
        String messageId = null;
		//check if organization paid for sender_id
		final SenderIdentifier senderId = findByOrganisationName(smsInfo.getSender());
		final String sender_id;
		if(senderId.getPaid().equals(Paid.YES)) {
			//set the sender_id 
			sender_id = senderId.getName();
		}else
			//else set the sender_id with default aeon_ sender_id
			sender_id = aeon_sender_id;
		
		try {
			//send sms_ to gateway and receive a response
            JSONArray results = gateway.sendMessage(smsInfo.getContacts(),
            		smsInfo.getMessage(), sender_id);
            messageId = results.getJSONObject(0).getString("messageId");
            
            for( int i = 0; i < results.length(); ++i ) {
                  JSONObject result = results.getJSONObject(i);
                  if(Integer.parseInt(result.getString("statusCode")) >= 101 &&
                		  Integer.parseInt(result.getString("statusCode")) <= 102) {                	  
                      //add to received list
                	  sent_list.add(new RecipientCost(result.getString("cost"), result.getString("number")));
//                      sent_phones.add(result.getString("number"));
                  }else if(Integer.parseInt(result.getString("statusCode")) >= 401 && 
                		  Integer.parseInt(result.getString("statusCode")) <= 407){
                	  //add to rejected list
                	  rejected_list.add(new RecipientCost(result.getString("cost"), result.getString("number")));
//                	  rejected_phones.add(result.getString("number"));
                  }
                  if(Integer.parseInt(result.getString("statusCode")) == 405) {                  
                	  //send email to aeon_ admin_ notifying them about the insufficient funds
                	  emailService.sendEmail(new EmailMessage(email, "sms can not be sent. Insufficient funds!", 
          					"sms could not be sent. Kindly top up"));
                  }                 
            }
            final BigDecimal cost_amount = BigDecimal.ZERO;
            //calculate the total cost from sent_list, this is assuming 
            //no amount was deducted from sending to the rejected list
            sent_list.parallelStream().forEach(recipient -> {
            	cost_amount.add(new BigDecimal(recipient.getAmount()));
            });
            rejected_list.parallelStream().forEach(recipient -> {
            	cost_amount.add(new BigDecimal(recipient.getAmount()));
            });
//            for(RecipientCost recipient: sent_list) {
//            	cost_amount = cost_amount.add(recipient.getAmount());
//            }
            
            //save to database the transaction cost(cost incurred by _aeon)
            final Organisation organisation = orgService.findByName(smsInfo.getSender());
            transService.save(new TransactionCost(cost_amount.setScale(2, RoundingMode.HALF_EVEN).doubleValue(), messageId, organisation));	
            
            return new OnSendDeliveryReport(messageId, sent_list, rejected_list);
        }catch (Exception e) {
        	log.info("***** Encountered an error while sending. Error: "+e.getMessage());
    		return new OnSendDeliveryReport("", null, null);
        }
	}

	@Override
	public SenderIdentifier findByOrganisationName(String orgName) {
		return repository.findByOrganisationName(orgName);
	}
	
	private static final Logger log = LoggerFactory.getLogger(SenderIdentifierServiceImpl.class);
}
