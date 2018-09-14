package co.ke.proaktiv.io.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import co.ke.proaktiv.io.events.OnUserRegistrationCompleteEvent;
import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.ClientAdmin;
import co.ke.proaktiv.io.pojos.reports.AdminReport;
import co.ke.proaktiv.io.pojos.reports.ClientReport;
import co.ke.proaktiv.io.pojos.reports.Report;
import co.ke.proaktiv.io.services.ClientAdminService;
import co.ke.proaktiv.io.services.ClientService;

@RestController
public class ClientAdminController {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientAdminService adminService;

	@PostMapping(value = "/admin/signin")
	public ResponseEntity<Object> signIn(
						@RequestParam("email") final String email){
		
		ClientReport report = new ClientReport();
		final Optional<ClientAdmin>  admin = adminService.findByEmail(email);
			if(!admin.isPresent())
				return new ResponseEntity<Object>(new ClientReport(400, "failed", 
						"user does not exist", new Client()), HttpStatus.OK);
			final Client client = admin.get().getClient();
			final Client response = new Client();
			response.setEnabled(client.isEnabled());
			report = new ClientReport(200, "success", "successfully signed in",	response);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@PostMapping(value = "/admin/{id}")
	public ResponseEntity<Object> save(
						@PathVariable("client") Long id, 						
						@RequestParam("admin") String email, 
						@RequestParam("password") String rawPassword, WebRequest request){

		ClientAdmin admin = new ClientAdmin();
		try {
			final Client client  = clientService.findById(id);		
			admin = adminService.save(new ClientAdmin(email, client));
			eventPublisher.publishEvent(new 
					OnUserRegistrationCompleteEvent(admin, request.getLocale(), rawPassword));
		} catch (Exception e) {
			return new ResponseEntity<Object>(new AdminReport(400, "failed", e.getMessage(), admin), 
					HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new AdminReport(200, "", "successfully created user", admin),
				HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/admin/{email}")
	public ResponseEntity<Object> delete(
						@PathVariable("email") String email){

			final Optional<ClientAdmin> admin = adminService.findByEmail(email);
			if(!admin.isPresent())
				return new ResponseEntity<Object>(new Report(400, "failed", "could not delete user"), 
						HttpStatus.OK);
			
			adminService.delete(admin.get());
		return new ResponseEntity<Object>(new Report(200, "success", "deleted successfully"), 
				HttpStatus.OK);
	}
}
