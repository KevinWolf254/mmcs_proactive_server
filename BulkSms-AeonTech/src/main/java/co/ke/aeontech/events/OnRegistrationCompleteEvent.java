package co.ke.aeontech.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import co.ke.aeontech.models.Organisation;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final Organisation organisation;

    public OnRegistrationCompleteEvent(final Organisation organisation, final Locale locale, final String appUrl) {
        super(organisation);
        this.organisation = organisation;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

}
