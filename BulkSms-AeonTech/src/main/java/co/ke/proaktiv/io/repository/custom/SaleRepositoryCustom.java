package co.ke.proaktiv.io.repository.custom;

import java.util.Date;
import java.util.List;

import co.ke.proaktiv.io.models.Sale;
/**
 * 
 * @author kevin
 * @version 1.0.0
 * @since 2018
 */
public interface SaleRepositoryCustom{
	/**
	 * finds all the sales made between a particular period
	 * @param from
	 * @param to
	 * @return
	 */
	public List<Sale> findBtwnDates(Date from, Date to);
	
	/**
	 * finds all sales made to a particular client between a
	 * particular period
	 * @param from
	 * @param to
	 * @param id
	 * @return
	 */
	public List<Sale> findBtwnDates(Date from, Date to, Long id);
}
