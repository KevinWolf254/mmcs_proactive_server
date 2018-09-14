package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Credit;
import co.ke.proaktiv.io.models.Sale;
import co.ke.proaktiv.io.pojos.reports.CreditReport;

/**
 * 
 * @author Kevin
 * @version 1.0.0
 * @since 2018
 */
public interface CreditService {
	
	/**
	 * finds credit info of a particular client
	 * @param client
	 * @return Credit
	 */
	public Credit findByClient(Client client);
	
	/**
	 * saves a credit object
	 * @param units
	 * @return Credit
	 */
	public Credit save(Credit credit);
	
	/**
	 * deletes a credit object
	 * @param credit
	 */
	public void delete(Credit credit);
	
	/**
	 * finds a credit report for a client
	 * it check for the credit remaining
	 * and also the payments that have
	 * not been disbursed to the client
	 * @param client
	 * @return CreditReport
	 */
	public CreditReport findCreditReport(Client client);

	/**
	 * when a client confirms a payment the amount of 
	 * the payment from the sale is added to the client
	 * credit
	 * @param client
	 * @param sale
	 * @return
	 */
	public Credit add(Client client, Sale sale);
	
	public Credit subtract(Client client, double amount);
}
