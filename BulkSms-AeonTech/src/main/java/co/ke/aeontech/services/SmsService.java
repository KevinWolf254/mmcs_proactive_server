package co.ke.aeontech.services;

import co.ke.aeontech.pojos.Sms;
import co.ke.aeontech.pojos.reports.SmsDeliveryReport;
import co.ke.aeontech.pojos.reports.SmsValidationReport;

public interface SmsService {

	/**
	 * sends an SMS to Africa's talking API
	 * @param smsInfo
	 * @return SmsDeliveryReport
	 */
	public SmsDeliveryReport sendSms(Sms smsInfo);

	/**
	 * checks if the SMS can be send or not
	 * @param sms
	 * @return
	 */
	public SmsValidationReport validateSms(Sms sms);
	
}
