package co.ke.aeontech.services;

import java.util.List;

import co.ke.aeontech.models.ProCharges;
import co.ke.aeontech.pojos.PhoneNos;
import co.ke.aeontech.pojos.helpers.Country;

public interface ProChargesService {

	public ProCharges findByCountry(Country country);

	public double getProCharges(List<PhoneNos> phoneNosTotals, Country country);
}
