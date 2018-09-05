package co.ke.aeontech.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ShortCode;
import co.ke.aeontech.services.ClientService;
import co.ke.aeontech.services.ShortCodeService;

@RestController
public class ShortCodeController {
	@Autowired
	private ShortCodeService shortCodeService;
	@Autowired
	private ClientService clientService;

	@GetMapping(value = "/shortcode/{client}/{name}")
	public ResponseEntity<ShortCode> register(@PathVariable("client") Long id,
			@PathVariable("name") String name){
		final Client client = clientService.findById(id);
		final ShortCode shortCode = shortCodeService.save(new ShortCode(name, client));
		return new ResponseEntity<ShortCode>(shortCode, HttpStatus.OK);
	}
	
	@PostMapping(value = "/shortcode")
	public ResponseEntity<Object> search(@RequestParam("value") String value){
		final Boolean shortCode = shortCodeService.exists(value);
		return new ResponseEntity<Object>(shortCode, HttpStatus.OK);
	}
	
}
