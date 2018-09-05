package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.Credit;
import co.ke.aeontech.models.Inventory;
import co.ke.aeontech.models.Sale;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.pojos.helpers.Currency;
import co.ke.aeontech.pojos.helpers.SaleType;
import co.ke.aeontech.pojos.reports.CreditReport;
import co.ke.aeontech.pojos.reports.DisbursmentReport;
import co.ke.aeontech.repository.CreditRepository;
import co.ke.aeontech.services.InventoryService;
import co.ke.aeontech.services.SaleService;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.ExchangeRatesService;
import co.ke.aeontech.services.CreditService;

@Service
public class CreditServiceImpl implements CreditService{

	@Autowired
	private CreditRepository repository;
	
	@Autowired
	private SaleService saleService;
	@Autowired
	private CreditService creditService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private ExchangeRatesService ratesService;
	@Autowired
	private EmailService emailService;

	@Value("${spring.mail.username}")
	private String proactiveEmail;

	
	@Override
	public Credit findByClient(final Client client){
		return repository.findByClient(client);
	}
	
	@Override
	public Credit save(Credit credit){
		final Credit credit_ = repository.save(credit);
		log.info("###### Saved: "+credit_);
		return credit_;
	} 	
	
	@Override
	public void delete(Credit credit) {
		repository.delete(credit);
	}

	@Override
	public CreditReport findCreditReport(final Client client) {		
		final Credit credit = findByClient(client);
		
		final DisbursmentReport pReport = saleService
				.findUnDisbursedPayments(client);
		
		return new CreditReport(200, "Credit Report", "Report for "+
					client.getName(), credit, pReport);
	}

	@Override
	public Credit add(final Client client, final Sale sale) {
		//get currency and amount that was paid
		final Currency sale_currency = sale.getPayment().getCurrency();
		final double unConvertedAmountPaid = sale.getPayment().getAmount();
		
		//get the credit and the currency of the client
		final Credit credit = creditService.findByClient(client);
		final Currency client_currency = credit.getCurrency();
		
		//convert the amount to the currency of the client
		final BigDecimal convertedAmountPaid = ratesService.convert(sale_currency,client_currency, 
				unConvertedAmountPaid);
		//find inventory associated with the client
		final Inventory _inventory = inventoryService.findByCurrency(client_currency);
		final double inventory = _inventory.getAmount();
		final BigDecimal inventory_ = BigDecimal.valueOf(inventory);
		
		if(inventory_.compareTo(convertedAmountPaid) == -1) {
			//send email to proactive_io_admin. with amount converted to kes_ for easy payment to AT
			final BigDecimal payThis = ratesService.convert(sale_currency, Currency.KES, 
					unConvertedAmountPaid);			
			final String emailMessage = "sale: "+sale.toString()
										+" made by client: "+client.toString()
										+" pay: "+Currency.KES
										+" "+payThis
										+" to top up their credit with amount: "+client_currency
										+" "+convertedAmountPaid;
			
			emailService.sendEmail(new EmailMessage(proactiveEmail, "title", emailMessage));
			return credit;
		}
		
		//deduct inventory
		_inventory.setAmount(inventory_.subtract(convertedAmountPaid)
				.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
		inventoryService.save(_inventory);
		//add client's credit
		final BigDecimal creditAmount = BigDecimal.valueOf(credit.getAmount());
		creditAmount.add(convertedAmountPaid).setScale(2, RoundingMode.HALF_EVEN);
		credit.setAmount(creditAmount.doubleValue());
		final Credit credit_ = creditService.save(credit);
		//save sale as credit disbursed
		sale.setType(SaleType.CREDIT);
		sale.setCreditDisbursed(Boolean.TRUE);
		saleService.save(sale);
		
		return credit_;
	}

	@Override
	public Credit subtract(Client client, double amount) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static final Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);
}
