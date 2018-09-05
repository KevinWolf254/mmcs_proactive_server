package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ShortCode;

public interface ShortCodeRepository extends JpaRepository<ShortCode, Long>{

	public ShortCode findByName(String name);
	
	public ShortCode findByClientId(Long id);
	
	public ShortCode findByClient(Client client);

	public ShortCode findByClientName(String name);
}
