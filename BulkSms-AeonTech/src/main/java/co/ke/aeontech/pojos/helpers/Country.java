package co.ke.aeontech.pojos.helpers;

public enum Country {

	RWANDA(250), KENYA(254), TANZANIA(255), UGANDA(256);
	
	private int code;
	
	private Country() {
	}
	private Country(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	
}
