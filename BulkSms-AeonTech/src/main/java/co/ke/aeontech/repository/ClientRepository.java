package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

	/**
	 * finds a client by its name
	 * @param name
	 * @return
	 */
	public Client findByName(String name);
}
