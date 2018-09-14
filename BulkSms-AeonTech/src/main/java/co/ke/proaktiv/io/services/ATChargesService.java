package co.ke.proaktiv.io.services;

import java.util.List;

import co.ke.proaktiv.io.models.ATCharges;
import co.ke.proaktiv.io.pojos.PhoneNos;
import co.ke.proaktiv.io.pojos.helpers.Country;

public interface ATChargesService {

	public ATCharges findByCountry(Country country);

	public double getATCharges(List<PhoneNos> phoneNosTotals, Country country);
}
