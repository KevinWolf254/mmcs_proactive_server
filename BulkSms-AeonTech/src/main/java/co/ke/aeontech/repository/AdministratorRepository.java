package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Long>{

	public Administrator findByEmail(String adminEmail);
	
	public Administrator findByOrganisationId(Long orgId);

}
