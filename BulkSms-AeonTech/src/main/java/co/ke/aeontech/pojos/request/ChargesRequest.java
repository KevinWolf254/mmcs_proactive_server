package co.ke.aeontech.pojos.request;

import java.util.List;

import co.ke.aeontech.pojos.PhoneNos;
import co.ke.aeontech.pojos.helpers.Country;

public class ChargesRequest {

	private Country country;
	private List<PhoneNos> phoneNosTotals;

	public ChargesRequest() {
		super();
	}

	public ChargesRequest(Country country, List<PhoneNos> phoneNosTotals) {
		super();
		this.country = country;
		this.phoneNosTotals = phoneNosTotals;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<PhoneNos> getPhoneNosTotals() {
		return phoneNosTotals;
	}

	public void setPhoneNosTotals(List<PhoneNos> phoneNosTotals) {
		this.phoneNosTotals = phoneNosTotals;
	}
	
}
