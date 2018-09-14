package co.ke.proaktiv.io.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Inventory;
import co.ke.proaktiv.io.pojos.helpers.Currency;
import co.ke.proaktiv.io.repository.InventoryRepository;
import co.ke.proaktiv.io.services.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository repository;
	
	@Override
	public Inventory findById(final Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Inventory findByCurrency(Currency currency) {
		return repository.findByCurrency(currency);
	}
	
	@Override
	public Inventory save(final Inventory aeon) {
		final Inventory inventory = repository.save(aeon);			
		log.info("###### Saved: "+inventory);	
		return inventory;
	}
	
	@Override
	public Inventory subtract(final Currency currency, final BigDecimal cost) {
		final Inventory inventory = repository.findByCurrency(currency);		
		final BigDecimal amount = BigDecimal.valueOf(inventory.getAmount());
		
		if(amount.compareTo(cost) == 1 || amount.compareTo(cost) == 0)
			amount.subtract(cost).setScale(2, RoundingMode.HALF_EVEN);
		else
			//subtract itself so as to be zero
			amount.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
		
		inventory.setAmount(amount.doubleValue());
		return save(inventory);		
	}
	private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
	
}
