package co.ke.aeontech.pojos.reports;

import co.ke.aeontech.models.Credit;

public class CreditReport extends Report {

	private Credit credit;
	private DisbursmentReport payment;
	
	public CreditReport() {
		super();
	}

	public CreditReport(int code, String title, String message, Credit credit, DisbursmentReport payment) {
		super(code, title, message);
		this.credit = credit;
		this.payment = payment;
	}

	public Credit getCredit() {
		return credit;
	}

	public void setCredit(Credit credit) {
		this.credit = credit;
	}

	public DisbursmentReport getPayment() {
		return payment;
	}

	public void setPayment(DisbursmentReport payment) {
		this.payment = payment;
	}
	
}
