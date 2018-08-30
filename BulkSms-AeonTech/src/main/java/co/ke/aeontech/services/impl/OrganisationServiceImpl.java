package co.ke.aeontech.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.repository.OrganisationRepository;
import co.ke.aeontech.services.OrganisationService;

@Service
public class OrganisationServiceImpl implements OrganisationService {
	
	@Autowired
	private OrganisationRepository repository;

	@Override
	public Organisation save(String name, String country) {
		Country _country = convertToCountryCode(country);	
		Organisation organisation = save(new Organisation(_country, name));		
		return organisation;
	}
	
	@Override
	public Organisation findById(Long org_id){
		Organisation found_by_id = repository.findById(org_id).get();
		return found_by_id;
	}

	@Override
	public Organisation findByName(String organisationName) {
		return repository.findByName(organisationName);
	}
	
	@Override
	public Organisation save(final Organisation organisation) { 
		log.info("###### saved: "+organisation);
		return repository.save(organisation);
	}

	@Override
	public void delete(Organisation organisation) {
		repository.delete(organisation);		
	}

	@Override
	public Organisation findByAdministratorsEmail(String email) {
		Organisation found_by_email = repository.findByAdministratorsEmail(email);
		return found_by_email;
	}
	
	@Override
	public Country convertToCountryCode(String country_code) {
		Country countryCode = null;
		if(country_code.equals("RWANDA"))
			countryCode = Country.RWANDA;
		else if(country_code.equals("KENYA"))
			countryCode = Country.KENYA;
		else if(country_code.equals("TANZANIA"))
			countryCode = Country.TANZANIA;
		else if(country_code.equals("UGANDA"))
			countryCode = Country.UGANDA;
		return countryCode;
	}
	private static final Logger log = LoggerFactory.getLogger(OrganisationServiceImpl.class);	
}
