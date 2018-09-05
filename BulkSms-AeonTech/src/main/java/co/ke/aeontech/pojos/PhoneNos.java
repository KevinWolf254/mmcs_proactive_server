package co.ke.aeontech.pojos;

import co.ke.aeontech.pojos.helpers.ServiceProvider;

public class PhoneNos {

	private ServiceProvider telecom;
	private int totals;
	public PhoneNos() {
		super();
	}
	public PhoneNos(ServiceProvider telecom, int totals) {
		super();
		this.telecom = telecom;
		this.totals = totals;
	}
	public int getTotals() {
		return totals;
	}
	public void setTotals(int totals) {
		this.totals = totals;
	}
	public ServiceProvider getTelecom() {
		return telecom;
	}
	public void setTelecom(ServiceProvider telecom) {
		this.telecom = telecom;
	}
}
