package co.ke.proaktiv.io.pojos.reports;

import co.ke.proaktiv.io.models.ClientAdmin;

public class AdminReport extends Report {

	private ClientAdmin admin;

	public AdminReport() {
		super();
	}

	public AdminReport(int code, String title, 
			String message, ClientAdmin admin) {
		super(code, title, message);
		this.admin = admin;
	}

	public ClientAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(ClientAdmin admin) {
		this.admin = admin;
	}
	
}