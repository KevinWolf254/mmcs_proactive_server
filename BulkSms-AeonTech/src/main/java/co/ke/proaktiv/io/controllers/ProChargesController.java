package co.ke.proaktiv.io.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.ProCharges;
import co.ke.proaktiv.io.pojos.helpers.Country;
import co.ke.proaktiv.io.pojos.reports.ChargesReport;
import co.ke.proaktiv.io.pojos.request.ChargesRequest;
import co.ke.proaktiv.io.services.ClientService;
import co.ke.proaktiv.io.services.ProChargesService;

@RestController
public class ProChargesController {

	@Autowired
	private ProChargesService chargesService;
	@Autowired
	private ClientService clientService;
	
	@PostMapping(value = "/charges")
	public ResponseEntity<Object> getCharges(
				@RequestBody final ChargesRequest request) {
		final double cost = chargesService.getProCharges(request.getPhoneNosTotals(), 
				request.getCountry());
		
		return new ResponseEntity<Object>(new ChargesReport(200, "Cost", 
				"Successfully retrieved the cost", cost), HttpStatus.OK);
	}	

	@GetMapping(value = "/charges/{id}")
	public ResponseEntity<ProCharges> getCharges(@PathVariable("id") final long id) {
		final Country country = clientService.findById(id).getCountry();
		final ProCharges charges = chargesService.findByCountry(country);
		
		return new ResponseEntity<ProCharges>(charges, HttpStatus.OK);
	}
}
