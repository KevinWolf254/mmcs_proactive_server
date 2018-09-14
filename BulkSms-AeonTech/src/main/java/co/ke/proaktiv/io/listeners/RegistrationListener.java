package co.ke.proaktiv.io.listeners;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.events.OnRegistrationCompleteEvent;
import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.ClientAdmin;
import co.ke.proaktiv.io.pojos.EmailMessage;
import co.ke.proaktiv.io.services.ClientAdminService;
import co.ke.proaktiv.io.services.EmailService;
import co.ke.proaktiv.io.services.TokenService;

@Service
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
    private TokenService tokenService;  
	@Autowired
	private ClientAdminService adminService;
	@Autowired
	private EmailService emailService;
	
    @Autowired
    private MessageSource messages;

	@Value("${mmcs.aeon.url}")
	private String Url;
	
    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final Client client = event.getClient();

        final String token = UUID.randomUUID().toString();
        
        //save token
        tokenService.createToken(client, token);

        //on registration only one administrator will be present in the database
		final Set<ClientAdmin> admins = adminService.findByClientId(client.getId());
		final String adminEmail = admins.stream()
				.collect(Collectors.toList())
				.get(0).getEmail();

        final String subject = "Account Activation";
        final StringBuilder builder = new StringBuilder();
		builder.append(messages.getMessage("message.regSucc", null, event.getLocale()))
				.append(" ")
				.append(Url)
				.append(event.getAppUrl())
				.append("/client/enable?token=")
				.append(token);
		String message =  builder.toString();
         
		emailService.sendEmail(new EmailMessage(adminEmail, subject, message));
    }

}
