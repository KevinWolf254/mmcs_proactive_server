package co.ke.aeontech.controllers;

import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import co.ke.aeontech.events.OnRegistrationCompleteEvent;
import co.ke.aeontech.events.OnUserRegistrationCompleteEvent;
import co.ke.aeontech.models.Administrator;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.SenderIdentifier;
import co.ke.aeontech.models.Units;
import co.ke.aeontech.models.VerificationToken;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.services.AdministratorService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.SenderIdentifierService;
import co.ke.aeontech.services.UnitsService;
import co.ke.aeontech.services.VerificationTokenService;

@RestController
public class OrganisationController {
	@Autowired
	private OrganisationService organisationService;
	@Autowired
	private SenderIdentifierService senderIdService;
	@Autowired
	private AdministratorService adminService;
	@Autowired
	private VerificationTokenService tokenService;
	@Autowired
	private UnitsService unitsService;	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Autowired
    private MessageSource messages; 
	
	@Value("${mmcs.client.url.signin}")
	private String signInUrl;
	@Value("${mmcs.client.url.register}")
	private String registrationUrl;	

	@PostMapping(value = "/register")
	public ResponseEntity<Organisation> register(
			@RequestParam("name") String name, 
			@RequestParam("country") String country,
			@RequestParam("admin") String email, 
			@RequestParam("senderId") String senderId,
			@RequestParam("code") String code,  
			@RequestParam("phoneNo") String phoneNo, WebRequest request){
		
		String appUrl = request.getContextPath();
		Country _country_code = organisationService.convertToCountryCode(code);
		
		Organisation organisation = organisationService.save(name, country);
		senderIdService.save(new SenderIdentifier(senderId, organisation));
		adminService.save(new Administrator(email, _country_code, phoneNo), organisation);
		unitsService.save(new Units(organisation));
		
		try {
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(organisation, request.getLocale(), appUrl));
		} catch (Exception e) {
			return new ResponseEntity<Organisation>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Organisation>( organisation, HttpStatus.OK);
	}
	@GetMapping(value = "/org/{id}")
	public ResponseEntity<Organisation> getOrganizationInfo (@PathVariable("id") Long org_id) {
		return new ResponseEntity<Organisation>( organisationService.findById(org_id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/regitrationConfirm")
	public String confirmRegistration (
			WebRequest request, 
			Model model, 
			@RequestParam("token") String token) {
	  
	    Locale locale = request.getLocale();
	    
	    VerificationToken verificationToken = tokenService.getToken(token);
	    if (verificationToken == null) {
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);	        
	        return "" + message;
	    }
	     
	    Organisation organisation = verificationToken.getOrganisation();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	//delete the token 
	    	tokenService.delete(verificationToken);
	    	//delete clientUnits
	    	Units units = unitsService.findByOrganisationId(organisation.getId());
	    	unitsService.delete(units);
	    	//delete the client & administrator
	    	organisationService.delete(organisation);
	        String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        model.addAttribute("message", messageValue);
	        return "" + messageValue +" " + registrationUrl;
	    } 
	     
	    organisation.setEnabled(true); 
	    organisationService.save(organisation);
	    tokenService.delete(verificationToken);
	    return "Successfully activated account. Click link to go to sign in: " + signInUrl; 
	}
	
	@GetMapping(value = "/org")
	public ResponseEntity<Object> getClient(@RequestParam("id") Long id){
		return new ResponseEntity<Object>(organisationService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/signin")
	public ResponseEntity<Organisation> signIn(@RequestParam("email") String email){
		Organisation company = organisationService.findByAdministratorsEmail(email);		
		return new ResponseEntity<Organisation>(company, HttpStatus.OK);
	}
	
	@PostMapping(value = "/add/user/{id}")
	public ResponseEntity<Object> saveUser(
			@PathVariable("id") Long org_id, 
			@RequestParam("email") String email, 
			@RequestParam("password") String rawPassword, WebRequest request){

		Organisation organisation  = organisationService.findById(org_id);		
		Administrator user = adminService.save(new Administrator(email, Country.KENYA, "0"), 
				organisation);
		
		try {
			eventPublisher.publishEvent(new OnUserRegistrationCompleteEvent(user, request.getLocale(), rawPassword));
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/user/{email}")
	public ResponseEntity<Object> deleteUser(
			@PathVariable("email") String email){
		Administrator user = adminService.findByEmail(email);
		adminService.delete(user);
		return new ResponseEntity<Object>("successfully deleted", HttpStatus.OK);
	}
}
