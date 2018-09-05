package co.ke.aeontech.listeners;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import co.ke.aeontech.events.OnRegistrationCompleteEvent;
import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ClientAdmin;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.services.ClientAdminService;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.TokenService;

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
	private String aeonUrl;
	
    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    @SuppressWarnings("unchecked")
	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final Client client = event.getClient();

        final String token = UUID.randomUUID().toString();
        
        //save token
        tokenService.createToken(client, token);

        //on registration only one administrator will be present in the database
		final List<ClientAdmin> admins = (List<ClientAdmin>) adminService
				.findByClientId(client.getId());
		final String adminEmail = admins.get(0).getEmail();

        final String subject = "Account Activation";
        final StringBuilder builder = new StringBuilder();
		builder.append(messages.getMessage("message.regSucc", null, event.getLocale()))
				.append(" ")
				.append(aeonUrl)
				.append(event.getAppUrl())
				.append("/regitrationConfirm?token=")
				.append(token);
		String message =  builder.toString();
         
		emailService.sendEmail(new EmailMessage(adminEmail, subject, message));
    }

}
