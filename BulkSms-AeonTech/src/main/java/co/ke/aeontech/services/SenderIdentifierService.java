package co.ke.aeontech.services;

import co.ke.aeontech.models.SenderIdentifier;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.SmsInfo;

public interface SenderIdentifierService {

	public void save(SenderIdentifier senderId);
	
	public SenderIdentifier findByOrganisationId(Long id);
	
	public SenderIdentifier findByOrganisationName(String orgName);

	public Boolean foundSenderIdByName(String senderId);
	
	public SenderIdentifier findByName(String senderId);

	public OnSendDeliveryReport sendSms(SmsInfo smsInfo);
}
