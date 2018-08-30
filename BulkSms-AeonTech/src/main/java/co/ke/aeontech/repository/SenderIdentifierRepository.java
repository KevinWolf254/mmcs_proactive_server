package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.SenderIdentifier;

public interface SenderIdentifierRepository extends JpaRepository<SenderIdentifier, Long>{

	public SenderIdentifier findByOrganisationId(Long id);

	public SenderIdentifier findByOrganisationName(String orgName);

	public SenderIdentifier findByName(String senderId);
}
