package co.ke.aeontech.services;

import java.math.BigDecimal;

import co.ke.aeontech.models.Inventory;
import co.ke.aeontech.pojos.helpers.Currency;

public interface InventoryService {

	public Inventory findById(final Long id);

	public Inventory findByCurrency(Currency currency);
	
	public Inventory subtract(Currency currency, BigDecimal cost);

	public Inventory save(final Inventory inventory);

}
