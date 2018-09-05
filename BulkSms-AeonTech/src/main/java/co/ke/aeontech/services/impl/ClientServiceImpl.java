package co.ke.aeontech.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.pojos.helpers.Country;
import co.ke.aeontech.repository.ClientRepository;
import co.ke.aeontech.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Override
	public Client findById(Long org_id){
		Client found_by_id = repository.findById(org_id).get();
		return found_by_id;
	}

	@Override
	public Client findByName(String organisationName) {
		return repository.findByName(organisationName);
	}
	
	@Override
	public Client save(final Client client) { 
		final Client client_ = repository.save(client);		
		log.info("###### saved: "+client_);
		return client_;
	}

	@Override
	public void delete(Client organisation) {
		repository.delete(organisation);		
	}
	
	@Override
	public Country getCountry(String country) {
		Country country_ = null;
		if(country.equals("RWANDA"))
			country_ = Country.RWANDA;
		else if(country.equals("KENYA"))
			country_ = Country.KENYA;
		else if(country.equals("TANZANIA"))
			country_ = Country.TANZANIA;
		else if(country.equals("UGANDA"))
			country_ = Country.UGANDA;
		return country_;
	}
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);	
}
