package co.ke.aeontech.services;

import co.ke.aeontech.models.Charges;
import co.ke.aeontech.pojos.Country;

public interface ChargesService {

	public Charges findByCountry(Country country);
}
