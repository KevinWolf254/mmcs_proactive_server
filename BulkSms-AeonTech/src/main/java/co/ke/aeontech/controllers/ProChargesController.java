package co.ke.aeontech.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.pojos.reports.ChargesReport;
import co.ke.aeontech.pojos.request.ChargesRequest;
import co.ke.aeontech.services.ProChargesService;

@RestController
public class ProChargesController {

	@Autowired
	private ProChargesService chargesService;
	
	@PostMapping(value = "/charges")
	public ResponseEntity<Object> getCharges(
				@RequestBody final ChargesRequest request) {
		final double cost = chargesService.getProCharges(request.getPhoneNosTotals(), 
				request.getCountry());
		
		return new ResponseEntity<Object>(new ChargesReport(200, "Cost", 
				"Successfully retrieved the cost", cost), HttpStatus.OK);
	}
}
