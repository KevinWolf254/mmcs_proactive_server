package co.ke.aeontech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Charges;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.repository.ChargesRepository;
import co.ke.aeontech.services.ChargesService;

@Service
public class ChargesServiceImpl implements ChargesService{

	@Autowired
	private ChargesRepository repository;
	
	@Override
	public Charges findByCountry(Country country) {		
		return repository.findByCountry(country);
	}

}
