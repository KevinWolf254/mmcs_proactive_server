package co.ke.aeontech.services;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.pojos.helpers.Country;

public interface ClientService {
	/**
	 * finds a client by its id
	 * @param id
	 * @return
	 */
	public Client findById(final Long id);
	
	/**
	 * finds a client by its name
	 * @param name
	 * @return
	 */
	public Client findByName(final String name);
	
	/**
	 * saves a new client
	 * @param client
	 * @return
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
