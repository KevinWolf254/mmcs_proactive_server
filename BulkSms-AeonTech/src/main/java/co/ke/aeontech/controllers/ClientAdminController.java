package co.ke.aeontech.controllers;

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

import co.ke.aeontech.events.OnUserRegistrationCompleteEvent;
import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ClientAdmin;
import co.ke.aeontech.pojos.reports.AdminReport;
import co.ke.aeontech.pojos.reports.ClientReport;
import co.ke.aeontech.pojos.reports.Report;
import co.ke.aeontech.services.ClientAdminService;
import co.ke.aeontech.services.ClientService;

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
		
		Client client = new Client();
		try {
			final ClientAdmin admin = adminService.findByEmail(email);		
			client = clientService.findById(admin.getClient().getId());
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ClientReport(400, "failed", e.getMessage(),
					client), HttpStatus.OK);
		}		
		return new ResponseEntity<Object>(new ClientReport(200, "success", "successfully signed in",
				client), HttpStatus.OK);
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

		try {
			final ClientAdmin admin = adminService.findByEmail(email);
			adminService.delete(admin);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new Report(400, "failed", e.getMessage()), 
					HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new Report(200, "success", "deleted successfully"), 
				HttpStatus.OK);
	}
}
