package co.ke.aeontech.pojos.reports;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.ClientAdmin;
import co.ke.aeontech.models.Credit;
import co.ke.aeontech.models.ShortCode;

public class SignUpReport extends Report {

	private Client client;
	private ShortCode shortCode;
	private ClientAdmin admin;
	private Credit credit;
	
	public SignUpReport() {
		super();
	}
	public SignUpReport(int code, String title, 
			String message, Client client, 
			ShortCode shortCode, ClientAdmin admin,
			Credit credit) {
		super(code, title, message);
		this.client = client;
		this.shortCode = shortCode;
		this.admin = admin;
		this.credit = credit;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public ShortCode getShortCode() {
		return shortCode;
	}
	public void setShortCode(ShortCode shortCode) {
		this.shortCode = shortCode;
	}
	public ClientAdmin getAdmin() {
		return admin;
	}
	public void setAdmin(ClientAdmin admin) {
		this.admin = admin;
	}
	public Credit getCredit() {
		return credit;
	}
	public void setCredit(Credit credit) {
		this.credit = credit;
	}
	
}
