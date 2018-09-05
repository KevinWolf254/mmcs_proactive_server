package co.ke.aeontech.services;

import java.math.BigDecimal;

import co.ke.aeontech.models.ExchangeRate;
import co.ke.aeontech.pojos.helpers.Country;
import co.ke.aeontech.pojos.helpers.Currency;

public interface ExchangeRatesService {

	public ExchangeRate findByCountry(Country country);
	
	public ExchangeRate save(ExchangeRate newRate);

	public BigDecimal convert(Currency from, Currency to, double amount);

	public Currency getCurrency(Country country);

	public Currency getCurrency(String currency_code);

	public Country getCountry(Currency currency);
	
//	public BigDecimal convertToCurrency(Currency convert_to_currency, BigDecimal amountRequested, ExchangeRate rates);
//
//	public BigDecimal convertFromCurrency(Currency convert_from_currency, BigDecimal supplier_units, ExchangeRate rates);
}
