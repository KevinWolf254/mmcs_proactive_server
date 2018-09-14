package co.ke.proaktiv.io.services;

import java.util.Optional;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Sale;
import co.ke.proaktiv.io.models.ShortCode;

/**
 * 
 * @author Kevin_
 * @version 1.0.0 
 * @since 2018
 */
public interface ShortCodeService {
	/**
	 * finds a shortCode by its name
	 * @param name
	 * @return
	 */
	public Optional<ShortCode> findByName(String name);
	
	/**
	 * finds a shortCode by its associated 
	 * client Object
	 * @param client
	 * @return
	 */
	public ShortCode findByClient(Client client);
	
	/**
	 * finds a shortCode by its associated
	 * client's id 
	 * @param id
	 * @return
	 */
	public ShortCode findByClientId(Long id);
	
	/**
	 * finds a shortCode by its associated
	 * client's name
	 * @param name
	 * @return
	 */
	public ShortCode findByClientName(String name);

	/**
	 * checks if a shortCode name already 
	 * exists or not
	 * @param name
	 * @return
	 */
	public Boolean exists(String name);
	
	/**
	 * saves a shortCode Object
	 * @param shortCode
	 * @return
	 */
	public ShortCode save(ShortCode shortCode);

	/**
	 * updates sale and also updates the paid 
	 * status of the short code
	 * @param client
	 * @param sale
	 * @return
	 */
	public ShortCode confirm(Client client, Sale sale);
	
}
