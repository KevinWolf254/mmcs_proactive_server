package co.ke.aeontech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.Charges;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.services.ChargesService;
import co.ke.aeontech.services.OrganisationService;

@RestController
public class ChargesController {

	@Autowired
	private ChargesService chargesService;
	@Autowired
	private OrganisationService orgService;
	
	@GetMapping(value = "/charges/{id}")
	public ResponseEntity<Charges> getCharges(@PathVariable("id") final long id) {
		Country country = orgService.findById(id).getCountry();
		Charges charges = chargesService.findByCountry(country);
		
		return new ResponseEntity<Charges>(charges, HttpStatus.OK);
	}
}
