package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.ExchangeRate;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.UnitsRequest;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.pojos.Request;
import co.ke.aeontech.repository.UnitsRequestRepository;
import co.ke.aeontech.services.ExchangeRatesService;
import co.ke.aeontech.services.PaymentService;
import co.ke.aeontech.services.UnitsRequestService;

@Service
public class UnitsRequestServiceImpl implements UnitsRequestService{
	@Autowired
	private UnitsRequestRepository repository;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private ExchangeRatesService ratesService;
	private BigDecimal total;

	@Override
	public UnitsRequest findByMpesaTransNo(String mpesaTransNo) {
		return repository.findByMpesaTransNo(mpesaTransNo);
	}
	
	@Override
	public List<UnitsRequest> findPendingByOrganisationId(final Long id) {
		return repository.findPendingByOrganisationId(id);
	}

	@Override
	public BigDecimal calculatePendingAmounts(final Organisation organisation, 
			final List<UnitsRequest> pending_requests) {
		
		final Country org_currency = organisation.getCountry();
		
		total = BigDecimal.ZERO;
		
		pending_requests.stream().forEach(request ->{
			final Country payment_currency = paymentService 
					.findByProviderRefId(request.getMpesaTransNo()).getCurrency();
			
			ExchangeRate rates = null;
			if(org_currency.equals(Country.RWANDA)) {
				//get exchange rates for RWANDA
				rates = ratesService.findByCountry(Country.RWANDA); 
			}else if(org_currency.equals(Country.KENYA)) {
				//get exchange rates for KENYA
				rates = ratesService.findByCountry(Country.KENYA);
			}else if(org_currency.equals(Country.TANZANIA)) {
				//get exchange rates for TANZANIA
				rates = ratesService.findByCountry(Country.TANZANIA);
			}else if(org_currency.equals(Country.UGANDA)) {
				//get exchange rates for UGANDA
				rates = ratesService.findByCountry(Country.UGANDA);
			}
			log.info("adding pending amounts before: "+total);

			total = total.add(ratesService.convertToCurrency(payment_currency,BigDecimal.valueOf(request.getAmountRequested()), rates));
			log.info("adding pending amounts after: "+total);
		});
		
		return total;
	}

	@Async
	@Override
	public void updatePendingUnits(List<UnitsRequest> pending_requests) {
		pending_requests.parallelStream().forEach(pendingRequest -> {	
			log.info("###### Updating from: "+pendingRequest);
			pendingRequest.setRequestStatus(Request.FULFILLED);	
			save(pendingRequest);
		});
	}
	
	//NB: Do not change to @Async
	@Override
	public void save(UnitsRequest requested) {
		final UnitsRequest request = repository.save(requested);
		log.info("###### Saved OK: "+request);
	}

	private static final Logger log = LoggerFactory.getLogger(UnitsRequestServiceImpl.class);

}
