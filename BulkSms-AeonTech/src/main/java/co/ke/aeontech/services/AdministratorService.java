package co.ke.aeontech.services;

import co.ke.aeontech.models.Administrator;
import co.ke.aeontech.models.Organisation;

public interface AdministratorService {

	public Administrator findByEmail(String adminEmail);
	
	public Administrator findByOrganisationId(Long orgId);

	public Administrator save(Administrator administrator, Organisation organisation);

	public void delete(Administrator user);
}
