package co.ke.proaktiv.io.pojos.request;

import co.ke.proaktiv.io.pojos.helpers.SaleType;

public class _Payment {

	private SaleType type;
	private String email;
	private double amount;
	private String mpesaNo;
	
	public _Payment() {
		super();
	}
	public _Payment(SaleType type, String email, double amount, 
			String mpesaNo) {
		super();
		this.type = type;
		this.email = email;
		this.amount = amount;
		this.mpesaNo = mpesaNo;
	}
	
	public SaleType getType() {
		return type;
	}
	public void setType(SaleType type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getMpesaNo() {
		return mpesaNo;
	}
	public void setMpesaNo(String mpesaNo) {
		this.mpesaNo = mpesaNo;
	}		
}
