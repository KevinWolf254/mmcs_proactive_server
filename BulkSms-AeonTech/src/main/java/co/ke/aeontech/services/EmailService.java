package co.ke.aeontech.services;

import co.ke.aeontech.pojos.EmailMessage;

/**
 * 
 * @author kevin
 * @version 1.0.0
 * @since 2018
 */
public interface EmailService {

	/**
	 * sends email
	 * @param email
	 */
	public void sendEmail(EmailMessage email);
}
