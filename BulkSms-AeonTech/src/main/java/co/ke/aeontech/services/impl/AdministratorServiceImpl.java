package co.ke.aeontech.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Administrator;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.repository.AdministratorRepository;
import co.ke.aeontech.services.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService{
	@Autowired
	private AdministratorRepository repository;

	@Override
	public Administrator findByEmail(String adminEmail) {
		return repository.findByEmail(adminEmail);
	}

	@Override
	public Administrator findByOrganisationId(Long orgId) {
		return repository.findByOrganisationId(orgId);
	}
	
	@Override
	public Administrator save(final Administrator administrator, final Organisation organisation) {
		administrator.setOrganisation(organisation);
		return repository.save(administrator);
	}

	@Override
	public void delete(Administrator user) {
		repository.delete(user);		
	}

}
