package co.ke.aeontech.listeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import co.ke.aeontech.events.OnRegistrationCompleteEvent;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.services.AdministratorService;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.VerificationTokenService;

@Service
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
    private VerificationTokenService tokenService;
  
	@Autowired
	private AdministratorService adminService;
	@Autowired
	private EmailService emailService;
	
    @Autowired
    private MessageSource messages;
  
//    @Autowired
//    private JavaMailSender mailSender;

	@Value("${mmcs.aeon.url}")
	private String aeonUrl;
	
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        final Organisation org = event.getOrganisation();

        String token = UUID.randomUUID().toString();
        
        tokenService.createToken(org, token);

        String adminEmail = adminService.findByOrganisationId(org.getId()).getEmail();

        String subject = "Account Activation";
        final StringBuilder builder = new StringBuilder();
		builder.append(messages.getMessage("message.regSucc", null, event.getLocale()))
				.append(" ")
				.append(aeonUrl)
				.append(event.getAppUrl())
				.append("/regitrationConfirm?token=")
				.append(token);
		String message =  builder.toString();
         
		emailService.sendEmail(new EmailMessage(adminEmail, subject, message));
//        SimpleMailMessage activationEmail = new SimpleMailMessage();
//        activationEmail.setTo(adminEmail);
//        activationEmail.setSubject(subject);
//        activationEmail.setText(message);
//        mailSender.send(activationEmail);
    }

}
