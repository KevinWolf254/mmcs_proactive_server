package co.ke.proaktiv.io.services;

import java.util.Optional;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.pojos.helpers.Country;

public interface ClientService {
	/**
	 * finds a client by its id
	 * @param id
	 * @return Client
	 */
	public Optional<Client> findById(final Long id);
	
	/**
	 * finds a client by its name
	 * @param name
	 * @return Client
	 */
	public Client findByName(final String name);
	
	/**
	 * finds a client by a administrator's email
	 * @param email
	 * @return Client
	 */
	public Client findByAdminsEmail(String email);
	
	/**
	 * saves a new client
	 * @param client
	 * @return Client
	 */
	public Client save(final Client client);
	
	/**
	 * deletes a client
	 * @param client
	 */
	public void delete(final Client client);

	/**
	 * converts a string e.g. RWANDA, KENYA, TANZANIA, UGANDA
	 * to type country
	 * @param code
	 * @return
	 */
	public Country getCountry(final String country);

}
