package co.ke.proaktiv.io.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.events.OnUserRegistrationCompleteEvent;
import co.ke.proaktiv.io.models.ClientAdmin;
import co.ke.proaktiv.io.pojos.EmailMessage;
import co.ke.proaktiv.io.services.EmailService;

@Service
public class UserRegistrationListener implements ApplicationListener<OnUserRegistrationCompleteEvent> {
  
	@Autowired
	private EmailService emailService;
	
    @Override
    public void onApplicationEvent(OnUserRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnUserRegistrationCompleteEvent event) {
        final ClientAdmin user = event.getUser();

        String subject = "Account Created";
        final StringBuilder builder = new StringBuilder();
		builder
				.append("Credentials: ")
				.append("Username: ")
				.append(user.getEmail())
				.append(" password: ")
				.append(event.getRawPassword());
		String message =  builder.toString();
         
		emailService.sendEmail(new EmailMessage(user.getEmail(), subject, message));
    }

}
