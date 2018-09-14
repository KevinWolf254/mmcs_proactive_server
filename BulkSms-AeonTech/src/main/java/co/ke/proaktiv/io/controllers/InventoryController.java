package co.ke.proaktiv.io.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Inventory;
import co.ke.proaktiv.io.pojos.helpers.Currency;
import co.ke.proaktiv.io.services.ClientService;
import co.ke.proaktiv.io.services.ExchangeRatesService;
import co.ke.proaktiv.io.services.InventoryService;

@RestController
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ExchangeRatesService ratesService;

	//update inventory with the paid amount
	@PutMapping(value = "/inventory")
	public ResponseEntity<Object> add(@RequestParam("client") final Long id,
			@RequestParam("amount") final double amount) {	
		
		final Client client = clientService.findById(id);
		final Currency currency = ratesService.getCurrency(client.getCountry());
		
		final Inventory inventory = inventoryService.findByCurrency(currency);
		final BigDecimal inventoryAmount = BigDecimal.valueOf(inventory.getAmount());
		//amount sent is already converted into the client's currency
		inventoryAmount.add(BigDecimal.valueOf(amount)).setScale(2, RoundingMode.HALF_EVEN);
		
		inventory.setAmount(inventoryAmount.doubleValue());
		final Inventory updated = inventoryService.save(inventory);
		return new ResponseEntity<Object>(updated, HttpStatus.OK);		
	}
}
