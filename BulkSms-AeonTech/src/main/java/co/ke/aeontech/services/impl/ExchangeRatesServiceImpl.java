package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.ExchangeRate;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.repository.ExchangeRatesRepository;
import co.ke.aeontech.services.ExchangeRatesService;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

	@Autowired
	private ExchangeRatesRepository repository;
	
	@Override
	public ExchangeRate findByCountry(Country country) {
		return repository.findByCountry(country);
	}
	
	@Async
	@Override
	public void save(ExchangeRate newRate) {
		repository.save(newRate);
		log.info("###### saved: "+newRate);
	}

	@Override
	public BigDecimal convertToCurrency(Country convert_to_currency, BigDecimal amountRequested, 
			ExchangeRate rates) {
//		double conversion = 0.0;
		BigDecimal conversion = BigDecimal.ZERO;
		if(convert_to_currency.equals(Country.RWANDA))
			conversion = amountRequested.divide(BigDecimal.valueOf(rates.getRwf()));
		else if(convert_to_currency.equals(Country.KENYA))
			conversion = amountRequested.divide(BigDecimal.valueOf(rates.getKes()));
//			conversion = amountRequested / rates.getKes();
		else if(convert_to_currency.equals(Country.TANZANIA))
			conversion = amountRequested.divide(BigDecimal.valueOf(rates.getTzs()));
//			conversion = amountRequested / rates.getTzs();
		else if(convert_to_currency.equals(Country.UGANDA))
			conversion = amountRequested.divide(BigDecimal.valueOf(rates.getUgx()));
//			conversion = amountRequested / rates.getUgx();
		return conversion.setScale(2, RoundingMode.HALF_EVEN);
	}
	
	@Override
	public BigDecimal convertFromCurrency(Country convert_from_currency, BigDecimal amountRequested, 
			ExchangeRate rates) {
		BigDecimal conversion = null;
		if(convert_from_currency.equals(Country.RWANDA))
			conversion = amountRequested.multiply(BigDecimal.valueOf(rates.getRwf()));
//			conversion = amountRequested * rates.getRwf();
		else if(convert_from_currency.equals(Country.KENYA))
			conversion = amountRequested.multiply(BigDecimal.valueOf(rates.getKes()));
//			conversion = amountRequested * rates.getKes();
		else if(convert_from_currency.equals(Country.TANZANIA))
			conversion = amountRequested.multiply(BigDecimal.valueOf(rates.getTzs()));
//			conversion = amountRequested * rates.getTzs();
		else if(convert_from_currency.equals(Country.UGANDA))
			conversion = amountRequested.multiply(BigDecimal.valueOf(rates.getUgx()));
//			conversion = amountRequested * rates.getUgx();
		return conversion.setScale(2, RoundingMode.HALF_EVEN);
	}
	private static final Logger log = LoggerFactory.getLogger(ExchangeRatesServiceImpl.class);
}
