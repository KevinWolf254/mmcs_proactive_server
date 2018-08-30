package co.ke.aeontech.pojos;

public enum Request {
PENDING("PENDING"), FULFILLED("FULFILLED");

	private final String status;
	
	private Request(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

}
