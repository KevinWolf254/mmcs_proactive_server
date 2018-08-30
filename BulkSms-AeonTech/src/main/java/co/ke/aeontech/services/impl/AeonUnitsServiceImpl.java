package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.AeonUnits;
import co.ke.aeontech.models.ExchangeRate;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.repository.AeonUnitsRepository;
import co.ke.aeontech.services.AeonUnitsService;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.ExchangeRatesService;

@Service
public class AeonUnitsServiceImpl implements AeonUnitsService {

	@Autowired
	private AeonUnitsRepository repository;

	@Autowired
	private ExchangeRatesService ratesService;
	@Autowired
	private EmailService emailService;


	@Value("${spring.mail.username}")
	private String proactiveEmail;
	
	private final static long AEON_ID = 1; 
	
	@Override
	public AeonUnits findById(final Long aeonId) {
		return repository.findById(aeonId).get();
	}
	
	@Async
	@Override
	public void updateAeonUnits(final double remaining_aeon_units) {
		
		if(remaining_aeon_units <= 500) {
			//send email to notify supplier they need to top up
			emailService.sendEmail(new EmailMessage(proactiveEmail, "Top up units.","units are less than 500"));
		}
			
		final AeonUnits aeonUnits = findById(AEON_ID);
		
		log.info("###### Updating from: "+aeonUnits);
		aeonUnits.setUnits(remaining_aeon_units);		
		save(aeonUnits);
	}
	
	@Override
	public void save(final AeonUnits aeon) {
		final AeonUnits aeonUnits = repository.save(aeon);			
		log.info("###### Saved: "+aeonUnits);	
	}

	@Override
	public BigDecimal convertAeonUnitsTo(final Country org_currency) {
		//check the exchange rates for Kenya_ since aeon_units are in _kes
		final ExchangeRate rates = ratesService.findByCountry(Country.KENYA);
		final BigDecimal supplier_units = new BigDecimal(findById(AEON_ID).getUnits());
		final BigDecimal transformed = ratesService.convertFromCurrency(org_currency, supplier_units, rates);					

		return transformed.setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public BigDecimal convertAeonUnitsFrom(final Country org_currency, final BigDecimal converted_aeon_units) {
		//check the exchange rates
		final ExchangeRate rates = ratesService.findByCountry(Country.KENYA);
		final BigDecimal transformed = ratesService.convertToCurrency(org_currency, converted_aeon_units, rates);					

		return transformed;
	}
	
	@Override
	public void subtractAeonUnits(final BigDecimal cost) {
		final AeonUnits AEON = findById(AEON_ID);
		
		final BigDecimal remaining_aeon_units = BigDecimal.ZERO;
		final BigDecimal aeonUnits = BigDecimal.valueOf(AEON.getUnits());
		
		if(aeonUnits.compareTo(cost) == 1)
			remaining_aeon_units.add(aeonUnits.subtract(cost));
		
		AEON.setUnits(remaining_aeon_units.doubleValue());
		save(AEON);		
	}
	
	private static final Logger log = LoggerFactory.getLogger(AeonUnitsServiceImpl.class);	
}
