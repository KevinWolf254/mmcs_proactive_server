package co.ke.proaktiv.io.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.ClientAdmin;
import co.ke.proaktiv.io.models.Cost;
import co.ke.proaktiv.io.models.Payment;
import co.ke.proaktiv.io.models.Sale;
import co.ke.proaktiv.io.pojos.EmailMessage;
import co.ke.proaktiv.io.pojos.fromAfricasTalking._Sale;
import co.ke.proaktiv.io.pojos.helpers.CostType;
import co.ke.proaktiv.io.pojos.helpers.Country;
import co.ke.proaktiv.io.pojos.helpers.Currency;
import co.ke.proaktiv.io.pojos.helpers.PaymentType;
import co.ke.proaktiv.io.pojos.helpers.SaleType;
import co.ke.proaktiv.io.pojos.helpers.Status;
import co.ke.proaktiv.io.pojos.reports.PendingDisbursementReport;
import co.ke.proaktiv.io.pojos.reports.SaleReport;
import co.ke.proaktiv.io.repository.SaleRepository;
import co.ke.proaktiv.io.services.ClientAdminService;
import co.ke.proaktiv.io.services.CostService;
import co.ke.proaktiv.io.services.EmailService;
import co.ke.proaktiv.io.services.ExchangeRatesService;
import co.ke.proaktiv.io.services.PaymentService;
import co.ke.proaktiv.io.services.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository repository;
	
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private CostService costService;
	@Autowired
	private ExchangeRatesService ratesService;
	@Autowired
	private ClientAdminService adminService;
	@Autowired
	private EmailService emailService;
	
	@Value("${spring.mail.username}")
	private String proactiveEmail;
	private BigDecimal total;
	@Override
	public Sale save(final Sale newPayment) {
		final Sale sale = repository.save(newPayment);
		log.info("Saved: "+sale);
		return sale;
	}

	@Override
	public Optional<Sale> findByCode(String code) {
		return repository.findByCode(code);
	}
	
	@Override
	public List<Sale> findByClient(final Client client) {
		return repository.findByClient(client);
	}
	
	@Override
	public Sale findByPayment(final Payment payment) {
		return repository.findByPayment(payment);
	}
	
	@Override
	public List<Sale> findByDate(final Date date) {
		return repository.findByDate(date);
	}
	
	@Override
	public List<Sale> findBtwnDates(final Date from, Date to) {
		return repository.findBtwnDates(from, to);
	}
	
	@Override
	public List<Sale> findBtwnDates(final Date from, final Date to, final Long id) {
		return repository.findBtwnDates(from, to, id);
	}

	@Override
	public List<Sale> findByCreditDisbursed(boolean confirm, Long id) {
		final List<Sale> sales = repository.findByClientId(id);
		final List<Sale> sales_ = new ArrayList<Sale>(); 
		
		sales.stream().filter(sale ->{
			return (sale.isCreditDisbursed() == confirm);
		}).forEach(sale->{
			sales_.add(sale);
		});
		return sales_;
	}
	
	@Override
	public SaleReport saveMpesa(final _Sale _sale) {
		final String value = _sale.getValue();
		final String currency_code = value.substring(0, 3);		
		final double amount = Double.parseDouble(value.substring(4).trim());
		
		final String saf_currency= _sale.getProviderFee().substring(0, 3);
		final Currency safCurrency = ratesService.getCurrency(saf_currency);
		final double saf_fee = Double.parseDouble(_sale.getProviderFee()
				.substring(4).trim());

		final String at_currency= _sale.getTransactionFee().substring(0, 3);
		final Currency atCurrency = ratesService.getCurrency(at_currency);
		final double at_fee = Double.parseDouble(_sale.getTransactionFee()
				.substring(4).trim());

		final Currency currency = ratesService.getCurrency(currency_code);

		final Status status = getStatus(_sale.getStatus());

		final Payment payment = paymentService.save(new Payment( 
				PaymentType.MPESAC2B, _sale.getClientAccount(), currency, amount));
		
		final Sale sale = new Sale(_sale.getProviderRefId(), SaleType.OTHER, status, payment);
		final Sale sale_ = save(sale);
		
		final Cost saf_cost = costService.save(new Cost(CostType.SALE, safCurrency, saf_fee, 
				sale.getCode()));
		costService.save(new Cost(CostType.SALE, atCurrency, at_fee, 
				sale.getCode()));
		
		//send email to proAktiv-io _admin notifying them about the sale
		emailService.sendEmail(new EmailMessage(proactiveEmail, "New Sale", "A new sale was made. Details"
				+ "are as follows: "+new SaleReport(sale_, payment, saf_cost).toString()));
		return new SaleReport(sale_, payment, saf_cost);
	}
	
	private Status getStatus(final String _status) {
		Status status;
		if(_status.equals(Status.SUCCESS.toString()))
			status = Status.SUCCESS;
		else
			status = Status.FAILED;
		return status;
	}
	
	@Override
	public PendingDisbursementReport findUnDisbursedPayments(final Client client) {
		final List<Sale> unDisbursed = findByCreditDisbursed(Boolean.FALSE, client.getId());
		final int numberOfPending = unDisbursed.size();
		
		final Country client_country = client.getCountry();
		final Currency client_currency = ratesService.getCurrency(client_country);
		
		total = BigDecimal.ZERO;
		
		unDisbursed.stream().forEach(sale ->{
			final Currency sale_currency = sale.getPayment().getCurrency();
			
			final BigDecimal sale_amount = ratesService
					.convert(sale_currency, client_currency, sale.getPayment().getAmount());
			
			total = total.add(sale_amount);
		});
		total.setScale(2, RoundingMode.HALF_EVEN);
		return new PendingDisbursementReport(200, "not disbursed", "report of undisbursed payments for "+
						client.getName(), client_currency, total, numberOfPending);
	}
	
	@Override
	public boolean validate(final Sale sale, final String email, final double amount) {
			
		if(sale.getPayment().getAmount() != amount) 
			return false;
		final ClientAdmin admin = adminService.findByEmail(email).get();
		final Client client = admin.getClient();
		if(sale.getStatus().equals(Status.FAILED)) {
			//confirm
			sale.setCreditDisbursed(Boolean.TRUE);
			//add client
			sale.setClient(client);
			//save sale
			save(sale);
			return false;
		}
		if(sale.getClient() != null) {
			if(sale.isCreditDisbursed())
				return false;
		}else {
			//set the client
			sale.setClient(client);
			save(sale);
		}
		return true;
	}
	
	private static final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);
}
