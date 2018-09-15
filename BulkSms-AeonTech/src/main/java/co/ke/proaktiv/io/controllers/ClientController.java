package co.ke.proaktiv.io.controllers;

import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import co.ke.proaktiv.io.events.OnRegistrationCompleteEvent;
import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.ClientAdmin;
import co.ke.proaktiv.io.models.Credit;
import co.ke.proaktiv.io.models.ShortCode;
import co.ke.proaktiv.io.models.Token;
import co.ke.proaktiv.io.pojos.reports.SignUpReport;
import co.ke.proaktiv.io.services.ClientAdminService;
import co.ke.proaktiv.io.services.ClientService;
import co.ke.proaktiv.io.services.CreditService;
import co.ke.proaktiv.io.services.ExchangeRatesService;
import co.ke.proaktiv.io.services.ShortCodeService;
import co.ke.proaktiv.io.services.TokenService;

@RestController
public class ClientController {
	@Autowired
	private ClientService clientService;
	@Autowired
	private ShortCodeService shortCodeService;
	@Autowired
	private ClientAdminService adminService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private ExchangeRatesService ratesService;
	@Autowired
	private CreditService creditService;	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Autowired
    private MessageSource messages; 
	
	@Value("${mmcs.client.url.signin}")
	private String signInUrl;
	@Value("${mmcs.client.url.register}")
	private String registrationUrl;	

	@GetMapping(value = "/client")
	public ResponseEntity<Object> findById(
						@RequestParam("id") final Long id){	
		final Optional<Client> client = clientService.findById(id);
		if(!client.isPresent())
			return new ResponseEntity<Object>(new Client(), HttpStatus.OK);
		return new ResponseEntity<Object>(client, HttpStatus.OK);
	}
	
	@PostMapping(value = "/client")
	public ResponseEntity<Object> save(
						@RequestParam("client") String name, 
						@RequestParam("country") String country,
						@RequestParam("admin") String email, 
						@RequestParam("shortCode") String sc_name, 
						@RequestParam("phoneNo") String phoneNo, WebRequest request){
		Client client = new Client();
		ShortCode shortCode = new ShortCode();
		ClientAdmin admin = new ClientAdmin();
		Credit credit = new Credit();
		try {
			
			if(shortCodeService.exists(sc_name)) {
				return new ResponseEntity<Object>(
						new SignUpReport(400, "Error", "Short Code already exists", 
								client, shortCode, admin, credit),
						HttpStatus.OK);
			}
			final String appUrl = request.getContextPath();
			
			client = clientService
					.save(new Client(clientService.getCountry(country), name));
			
			shortCode = shortCodeService
					.save(new ShortCode(sc_name, client));
			
			admin = adminService
					.save(new ClientAdmin(email, phoneNo, client));
			
			credit = creditService
					.save(new Credit(ratesService.getCurrency(client.getCountry()), 
							client));
		
			eventPublisher
				.publishEvent(new OnRegistrationCompleteEvent(client, 
						request.getLocale(), appUrl));
		} catch (Exception e) {
			log.info("error:"+e.getMessage());
			return new ResponseEntity<Object>(
					new SignUpReport(500, "Error", e.getMessage(), 
							client, shortCode, admin, credit),
					HttpStatus.OK);
		}
		
		return new ResponseEntity<Object>( 
				new SignUpReport(200, "success","created organization successfully", 
						client, shortCode, admin, credit),
				HttpStatus.OK);
	}
		
	@GetMapping(value = "/client/enable")
	public String enable(final WebRequest request, 
			final Model model, @RequestParam("token") final String token) {
	  
	    final Locale locale = request.getLocale();
	    
	    final Token token_ = tokenService.getToken(token);
	    if (token_ == null) {
	        final String message = messages
	        		.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);	        
	        return "" + message;
	    }
	     
	    final Client client = token_.getClient();
	    final Calendar cal = Calendar.getInstance();
	    if ((token_.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	//delete the token 
	    	tokenService.delete(token_);
	    	//delete clientUnits
	    	final Credit credit_ = creditService.findByClient(client);
	    	creditService.delete(credit_);
	    	//delete the client & administrator
	    	clientService.delete(client);
	        String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        model.addAttribute("message", messageValue);
	        return "" + messageValue +" " + registrationUrl;
	    } 
	     
	    client.setEnabled(true); 
	    clientService.save(client);
	    tokenService.delete(token_);
	    return "Successfully activated account. Click link to go to sign in: " + signInUrl; 
	}
	private static final Logger log = LoggerFactory.getLogger(ClientController.class);

}
