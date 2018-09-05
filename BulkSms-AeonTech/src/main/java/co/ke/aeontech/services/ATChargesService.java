package co.ke.aeontech.services;

import java.util.List;

import co.ke.aeontech.models.ATCharges;
import co.ke.aeontech.pojos.PhoneNos;
import co.ke.aeontech.pojos.helpers.Country;

public interface ATChargesService {

	public ATCharges findByCountry(Country country);

	public double getATCharges(List<PhoneNos> phoneNosTotals, Country country);
}
