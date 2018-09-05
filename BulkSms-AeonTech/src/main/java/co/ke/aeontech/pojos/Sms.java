package co.ke.aeontech.pojos;

import java.util.List;

public class Sms {

	private String sender;
	private String recipients;
	private List<PhoneNos> phoneNosTotals;
	private String message;
	public Sms() {
		super();
	}
	public Sms(String sender, String recipients, 
			List<PhoneNos> phoneNosTotals, String message) {
		super();
		this.sender = sender;
		this.recipients = recipients;
		this.phoneNosTotals = phoneNosTotals;
		this.message = message;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<PhoneNos> getPhoneNosTotals() {
		return phoneNosTotals;
	}
	public void setPhoneNosTotals(List<PhoneNos> phoneNosTotals) {
		this.phoneNosTotals = phoneNosTotals;
	}
}
