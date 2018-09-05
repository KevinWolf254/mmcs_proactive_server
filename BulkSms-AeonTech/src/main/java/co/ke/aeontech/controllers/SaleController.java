package co.ke.aeontech.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ClientAdmin;
import co.ke.aeontech.models.Sale;
import co.ke.aeontech.pojos.fromAfricasTalking._Sale;
import co.ke.aeontech.pojos.helpers.SaleType;
import co.ke.aeontech.pojos.reports.Report;
import co.ke.aeontech.pojos.reports.SaleReport;
import co.ke.aeontech.pojos.request._Payment;
import co.ke.aeontech.services.ClientAdminService;
import co.ke.aeontech.services.ClientService;
import co.ke.aeontech.services.CreditService;
import co.ke.aeontech.services.SaleService;
import co.ke.aeontech.services.ShortCodeService;

@RestController
public class SaleController {
	@Autowired
	private SaleService saleService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientAdminService adminService;
	@Autowired
	private CreditService creditService;
	@Autowired
	private ShortCodeService shortCodeService;
	
	@PostMapping(value = "/sale")
	public ResponseEntity<Object> save(@RequestBody _Sale _sale) {
		
		final SaleReport report = saleService.saveMpesa(_sale);		
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/sale/{id}")
	public ResponseEntity<Object> findByClient(@PathVariable("id") Long id){
		
		final Client client = clientService.findById(id);
		final List<Sale> sales = saleService.findByClient(client);
		return new ResponseEntity<Object>(sales, HttpStatus.OK);
	}
	
	@PostMapping(value = "/sale/find")
	public ResponseEntity<Object> findBtwnDates(@RequestParam("id") Long id,
		@RequestParam("from") final Date from, @RequestParam("to") final Date to){
		
		final List<Sale> payments = saleService
				.findBtwnDates(from, to, id);
		return new ResponseEntity<Object>(payments, HttpStatus.OK);
	}
	
	@PutMapping(value = "/sale")
	public ResponseEntity<Object> confirm(@RequestBody final _Payment confirmation) {

		final Optional<Sale> sale = saleService.findByCode(confirmation.getMpesaNo());
		if(!sale.isPresent()) 
			return new ResponseEntity<Object>(
					new Report(400, "failed", "invalid payment confirmation"),
					HttpStatus.OK);
		
		final boolean valid = saleService
				.validate(sale.get(), confirmation.getEmail(), confirmation.getAmount());
		
		if(!valid)
			return new ResponseEntity<Object>(
					new Report(400, "failed", "invalid payment confirmation"),
					HttpStatus.OK);
		
		//get client
		final ClientAdmin admin = adminService.findByEmail(confirmation.getEmail());
		final Client client = admin.getClient();
		
		//check if sale was for short code 
		if(confirmation.getType().equals(SaleType.SHORT_CODE)) {
			shortCodeService.confirm(client, sale.get());
			
			return new ResponseEntity<Object>(
					new Report(200, "success", "short code confirmation successful"),
					HttpStatus.OK);	
		}
		//add client's credit
		creditService.add(client, sale.get());
		
		return new ResponseEntity<Object>(
				new Report(200, "success", "credit confirmation successful"),
				HttpStatus.OK);		
	}
}
