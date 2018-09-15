package co.ke.proaktiv.io.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.pojos.helpers.Country;
import co.ke.proaktiv.io.repository.ClientRepository;
import co.ke.proaktiv.io.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Override 
	public Optional<Client> findById(Long org_id){
		final Optional<Client> client = repository.findById(org_id);
		return client;
	}

	@Override
	public Client findByName(String organisationName) {
		return repository.findByName(organisationName);
	}
	
	@Override
	public Client findByAdminsEmail(String email) {
		return repository.findByAdminsEmail(email);
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
