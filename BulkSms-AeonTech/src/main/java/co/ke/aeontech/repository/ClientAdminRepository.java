package co.ke.aeontech.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ClientAdmin;

public interface ClientAdminRepository extends JpaRepository<ClientAdmin, Long>{

	public Optional<ClientAdmin>  findByEmail(String email);
	
	public Set<ClientAdmin> findByClient(Client client);

	public Set<ClientAdmin> findByClientId(Long id);
}
