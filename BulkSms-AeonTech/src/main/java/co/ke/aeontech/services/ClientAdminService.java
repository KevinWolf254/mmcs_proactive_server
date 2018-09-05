package co.ke.aeontech.services;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ClientAdmin;

import java.util.Set;

/**
 * 
 * @author Kevin_
 * @version 1.0
 * @since 2018
 */
public interface ClientAdminService {

	/**
	 * finds an administrator by their email
	 * @param adminEmail
	 * @return 
	 */
	public ClientAdmin findByEmail(String adminEmail);
	
	/**
	 * finds all administrators of a certain client 
	 * @param client
	 * @return
	 */
	public Set<ClientAdmin> findByClient(Client client);

	/**
	 * finds all administrators  by the associated 
	 * client's id
	 * @param id
	 * @return
	 */
	public Set<ClientAdmin> findByClientId(Long id);
	
	/**
	 * saves a new administrator
	 * @param admin
	 * @return
	 */
	public ClientAdmin save(ClientAdmin admin);

	/**
	 * deletes an administrator
	 * @param admin
	 */
	public void delete(ClientAdmin admin);
}
