package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long>{

	Organisation findByName(String organisationName);

	Organisation findByAdministratorsEmail(String email);

}
