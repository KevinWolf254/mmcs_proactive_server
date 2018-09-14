package co.ke.proaktiv.io.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.ShortCode;

public interface ShortCodeRepository extends JpaRepository<ShortCode, Long>{

	public Optional<ShortCode> findByName(String name);
	
	public ShortCode findByClientId(Long id);
	
	public ShortCode findByClient(Client client);

	public ShortCode findByClientName(String name);
}
