package co.ke.proaktiv.io.pojos.reports;

import co.ke.proaktiv.io.models.Credit;

public class CreditReport extends Report {

	private Credit credit;
	private PendingDisbursementReport payment;
	
	public CreditReport() {
		super();
	}

	public CreditReport(int code, String title, String message, Credit credit, PendingDisbursementReport payment) {
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

	public PendingDisbursementReport getPayment() {
		return payment;
	}

	public void setPayment(PendingDisbursementReport payment) {
		this.payment = payment;
	}
	
}
