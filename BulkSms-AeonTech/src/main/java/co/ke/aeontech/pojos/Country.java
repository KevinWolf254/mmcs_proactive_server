package co.ke.aeontech.pojos;

public enum Country {

	RWANDA("RWF", 250), KENYA("KES", 254), TANZANIA("TZS", 255), UGANDA("UGX", 256);
	
	private String acronym;
	private int countryCode;
	
	private Country() {
	}
	private Country(String acronym, int countryCode) {
		this.acronym = acronym;
		this.countryCode = countryCode;
	}
	public String getAcronym() {
		return acronym;
	}
	public int getCountryCode() {
		return countryCode;
	}
	
}
