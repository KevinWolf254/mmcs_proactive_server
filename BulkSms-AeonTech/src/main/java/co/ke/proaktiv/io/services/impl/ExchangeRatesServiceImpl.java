package co.ke.proaktiv.io.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.ExchangeRate;
import co.ke.proaktiv.io.pojos.helpers.Country;
import co.ke.proaktiv.io.pojos.helpers.Currency;
import co.ke.proaktiv.io.repository.ExchangeRatesRepository;
import co.ke.proaktiv.io.services.ExchangeRatesService;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

	@Autowired
	private ExchangeRatesRepository repository;
	
	@Override
	public ExchangeRate findByCountry(final Country country) {
		return repository.findByCountry(country);
	}
	
	@Override
	public ExchangeRate save(final ExchangeRate newRate) {
		final ExchangeRate rate = repository.save(newRate);
		log.info("###### saved: "+rate);
		return rate;
	}

	@Override
	public BigDecimal convert(final Currency from, final Currency to, final double amount) {
		final BigDecimal amount_ = BigDecimal.valueOf(amount);
		final ExchangeRate rate = repository.findByCountry(getCountry(from));
		if(to.equals(Currency.RWF)) {
			amount_.multiply(BigDecimal.valueOf(rate.getRwf()));
		}else if(to.equals(Currency.KES)) {
			amount_.multiply(BigDecimal.valueOf(rate.getKes()));
		}else if(to.equals(Currency.TZS)) {
			amount_.multiply(BigDecimal.valueOf(rate.getTzs()));
		}else if(to.equals(Currency.UGX)) {
			amount_.multiply(BigDecimal.valueOf(rate.getUgx()));
		}else
			amount_.multiply(BigDecimal.valueOf(rate.getDollar()));
		return amount_.setScale(2, RoundingMode.HALF_EVEN);
	}
	
	@Override
	public Currency getCurrency(final Country country) {
		final Currency currency;
		if(country.equals(Country.RWANDA))
			currency = Currency.RWF;
		else if(country.equals(Country.KENYA))
			currency = Currency.KES;
		else if(country.equals(Country.TANZANIA))
			currency = Currency.TZS;
		else 
			currency = Currency.UGX;
		return currency;
	}
	
	public Currency getCurrency(final String currency_code) {
		Currency currency = null;
		
		if(currency_code.equals("RWF")) {
			currency = Currency.RWF;
		}else if(currency_code.equals("KES")){
			currency = Currency.KES;
		}else if(currency_code.equals("TZS")){
			currency = Currency.TZS;
		}else if(currency_code.equals("UGX")){
			currency = Currency.UGX;
		}
		log.info("converted currency: "+currency_code+ " to "+currency);

		return currency;
	}
	
	@Override
	public Country getCountry(final Currency currency) {
		final Country country;
		if(currency.equals(Currency.RWF))
			country = Country.RWANDA;
		else if(currency.equals(Currency.KES ))
			country = Country.KENYA;
		else if(currency.equals(Currency.TZS))
			country = Country.TANZANIA;
		else 
			country = Country.UGANDA;
		return country;
	}
//	@Override
//	public BigDecimal convertToCurrency(Currency convert_to_currency, BigDecimal amountRequested, 
//			ExchangeRate rates) {
////		double conversion = 0.0;
//		BigDecimal conversion = BigDecimal.ZERO;
//		if(convert_to_currency.equals(Currency.RWF))
//			conversion = amountRequested.divide(BigDecimal.valueOf(rates.getRwf()));
//		else if(convert_to_currency.equals(Currency.KES))
//			conversion = amountRequested.divide(BigDecimal.valueOf(rates.getKes()));
////			conversion = amountRequested / rates.getKes();
//		else if(convert_to_currency.equals(Currency.TZS))
//			conversion = amountRequested.divide(BigDecimal.valueOf(rates.getTzs()));
////			conversion = amountRequested / rates.getTzs();
//		else if(convert_to_currency.equals(Currency.UGX))
//			conversion = amountRequested.divide(BigDecimal.valueOf(rates.getUgx()));
////			conversion = amountRequested / rates.getUgx();
//		return conversion.setScale(2, RoundingMode.HALF_EVEN);
//	}
//	
//	@Override
//	public BigDecimal convertFromCurrency(Currency convert_from_currency, BigDecimal amountRequested, 
//			ExchangeRate rates) {
//		BigDecimal conversion = null;
//		if(convert_from_currency.equals(Currency.RWF))
//			conversion = amountRequested.multiply(BigDecimal.valueOf(rates.getRwf()));
////			conversion = amountRequested * rates.getRwf();
//		else if(convert_from_currency.equals(Currency.KES))
//			conversion = amountRequested.multiply(BigDecimal.valueOf(rates.getKes()));
////			conversion = amountRequested * rates.getKes();
//		else if(convert_from_currency.equals(Currency.TZS))
//			conversion = amountRequested.multiply(BigDecimal.valueOf(rates.getTzs()));
////			conversion = amountRequested * rates.getTzs();
//		else if(convert_from_currency.equals(Currency.UGX))
//			conversion = amountRequested.multiply(BigDecimal.valueOf(rates.getUgx()));
////			conversion = amountRequested * rates.getUgx();
//		return conversion.setScale(2, RoundingMode.HALF_EVEN);
//	}
	private static final Logger log = LoggerFactory.getLogger(ExchangeRatesServiceImpl.class);
}
