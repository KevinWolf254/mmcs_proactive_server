package co.ke.aeontech.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import co.ke.aeontech.models.Administrator;

@SuppressWarnings("serial")
public class OnUserRegistrationCompleteEvent extends ApplicationEvent {

    private final String rawPassword;
    private final Locale locale;
    private final Administrator user;
	public OnUserRegistrationCompleteEvent(final Administrator user, final Locale locale, final String rawPassword) {
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
	public Administrator getUser() {
		return user;
	}
}
