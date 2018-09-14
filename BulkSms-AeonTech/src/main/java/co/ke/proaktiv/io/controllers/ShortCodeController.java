package co.ke.proaktiv.io.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.ShortCode;
import co.ke.proaktiv.io.pojos.reports.ShortCodeReport;
import co.ke.proaktiv.io.services.ClientService;
import co.ke.proaktiv.io.services.ShortCodeService;

@RestController
public class ShortCodeController {
	@Autowired
	private ShortCodeService shortCodeService;
	@Autowired
	private ClientService clientService;

	@GetMapping(value = "/shortcode/{client}/{name}")
	public ResponseEntity<Object> save(@PathVariable("client") Long id,
			@PathVariable("name") String name){
		final Client client = clientService.findById(id);
		final ShortCode shortCode = shortCodeService.save(new ShortCode(name, client));
		return new ResponseEntity<Object>(new 
				ShortCodeReport(200, "Success", 
						"Saved short code", shortCode), HttpStatus.OK);
	}
	
	@PostMapping(value = "/shortcode")
	public ResponseEntity<Object> findByName(@RequestParam("name") String name){
		
		final Optional<ShortCode> shortCode = shortCodeService.findByName(name);
		
		if(!shortCode.isPresent())
			return new ResponseEntity<Object>(new 
					ShortCodeReport(400, "Invalid Request", 
							"no short code with that name present", null), 
					HttpStatus.OK);
		return new ResponseEntity<Object>(new 
				ShortCodeReport(200, "Success", 
						"Short code with that name present", shortCode.get()), HttpStatus.OK);
	}
	
}
