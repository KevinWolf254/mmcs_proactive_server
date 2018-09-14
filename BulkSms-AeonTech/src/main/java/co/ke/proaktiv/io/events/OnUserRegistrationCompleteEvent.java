package co.ke.proaktiv.io.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import co.ke.proaktiv.io.models.ClientAdmin;

@SuppressWarnings("serial")
public class OnUserRegistrationCompleteEvent extends ApplicationEvent {

    private final String rawPassword;
    private final Locale locale;
    private final ClientAdmin user;
	public OnUserRegistrationCompleteEvent(final ClientAdmin user, final Locale locale, final String rawPassword) {
		super(user);
		this.rawPassword = rawPassword;
		this.locale = locale;
		this.user = user;
	}
	public String getRawPassword() {
		return rawPassword;
	}
	public Locale getLocale() {
		return locale;
	}
	public ClientAdmin getUser() {
		return user;
	}
}
