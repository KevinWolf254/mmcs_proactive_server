package co.ke.aeontech.pojos.reports;

import co.ke.aeontech.models.Client;

public class ClientReport extends Report {

	private Client client;

	public ClientReport() {
		super();
	}

	public ClientReport(int code, String title, 
			String message, Client client) {
		super(code, title, message);
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
}
