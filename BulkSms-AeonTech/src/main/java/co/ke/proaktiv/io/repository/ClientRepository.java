package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

	/**
	 * finds a client by its name
	 * @param name
	 * @return
	 */
	public Client findByName(String name);
}
