package co.ke.aeontech.services;

import java.math.BigDecimal;

import co.ke.aeontech.models.ExchangeRate;
import co.ke.aeontech.pojos.Country;

public interface ExchangeRatesService {

	public ExchangeRate findByCountry(Country country);
	
	public void save(ExchangeRate newRate);
	
	public BigDecimal convertToCurrency(Country convert_to_currency, BigDecimal amountRequested, ExchangeRate rates);

	public BigDecimal convertFromCurrency(Country convert_from_currency, BigDecimal supplier_units, ExchangeRate rates);
}
