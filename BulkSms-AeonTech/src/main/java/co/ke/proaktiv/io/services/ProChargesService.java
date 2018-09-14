package co.ke.proaktiv.io.services;

import java.util.List;

import co.ke.proaktiv.io.models.ProCharges;
import co.ke.proaktiv.io.pojos.PhoneNos;
import co.ke.proaktiv.io.pojos.helpers.Country;

public interface ProChargesService {

	public ProCharges findByCountry(Country country);

	public double getProCharges(List<PhoneNos> phoneNosTotals, Country country);
}
