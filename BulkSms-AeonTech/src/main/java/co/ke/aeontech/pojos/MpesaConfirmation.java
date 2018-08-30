package co.ke.aeontech.pojos;

public class MpesaConfirmation {

	private String requestedBy;
	private double requestedUnits;
	private String mpesaTransNo;
	
	public MpesaConfirmation() {
		super();
	}
	public MpesaConfirmation(String requestedBy, double requestedUnits, 
			String mpesaTransNo) {
		super();
		this.requestedBy = requestedBy;
		this.requestedUnits = requestedUnits;
		this.mpesaTransNo = mpesaTransNo;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public double getRequestedUnits() {
		return requestedUnits;
	}
	public void setRequestedUnits(double requestedUnits) {
		this.requestedUnits = requestedUnits;
	}
	public String getMpesaTransNo() {
		return mpesaTransNo;
	}
	public void setMpesaTransNo(String mpesaTransNo) {
		this.mpesaTransNo = mpesaTransNo;
	}
	@Override
	public String toString() {
		return "AddUnitsRequest [requestedBy=" + requestedBy + ", requestedUnits="
	+ requestedUnits + ", mpesaTransNo=" + mpesaTransNo + "]";
	}
		
}
