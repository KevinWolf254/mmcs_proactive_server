package co.ke.aeontech.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ClientAdmin;
import co.ke.aeontech.models.Credit;
import co.ke.aeontech.pojos.reports.CreditReport;
import co.ke.aeontech.services.InventoryService;
import co.ke.aeontech.services.ClientAdminService;
import co.ke.aeontech.services.ClientService;
import co.ke.aeontech.services.CreditService;

@RestController
public class CreditController {
	
	@Autowired
	private CreditService creditService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientAdminService adminService;
	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping(value = "/credit/{email}")
	public ResponseEntity<Object> find(@PathVariable("email") final String email) {
		
		final ClientAdmin admin = adminService.findByEmail(email);
		final Client client = admin.getClient();
		final CreditReport report = creditService.findCreditReport(client);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/credit/{id}")
	public ResponseEntity<Object> findByClientId(@PathVariable("id") final Long id) {
		
		final Client client = clientService.findById(id);
		final CreditReport report = creditService.findCreditReport(client);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@PutMapping(value = "/credit")
	public ResponseEntity<Object> subtract(@RequestParam("client") final Long id,
			@RequestParam("amount") final double amount) {
		
		final Client client = clientService.findById(id);
		final Credit credit = creditService.findByClient(client);
		
		final BigDecimal available = BigDecimal.valueOf(credit.getAmount());
		final BigDecimal expense = BigDecimal.valueOf(amount);

		available.subtract(expense).setScale(2, RoundingMode.HALF_EVEN);
		credit.setAmount(available.doubleValue());
		final Credit cUpdated = creditService.save(credit);
		
		//update inventory associated with the organization
		inventoryService.subtract(credit.getCurrency(), expense);
		
		return new ResponseEntity<Object>(cUpdated, HttpStatus.OK);
	}

}
