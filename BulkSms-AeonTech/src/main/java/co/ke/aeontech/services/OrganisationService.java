package co.ke.aeontech.services;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.pojos.Country;

public interface OrganisationService {

	public Organisation save(String name, String country);
	
	public Organisation findById(final Long id);
	
	public Organisation findByName(final String organisationName);
	
	public Organisation save(final Organisation client);
	
	public void delete(Organisation organisation);

	public Organisation findByAdministratorsEmail(String email);

	public Country convertToCountryCode(String code);

}
