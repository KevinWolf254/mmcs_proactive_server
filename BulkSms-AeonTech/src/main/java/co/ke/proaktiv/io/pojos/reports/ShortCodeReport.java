package co.ke.proaktiv.io.pojos.reports;

import co.ke.proaktiv.io.models.ShortCode;

public class ShortCodeReport extends Report {

	private ShortCode shortCode;

	public ShortCodeReport() {
		super();
	}

	public ShortCodeReport(int code, String title, String message, ShortCode shortCode) {
		super(code, title, message);
		this.shortCode = shortCode;
	}

	public ShortCode getShortCode() {
		return shortCode;
	}

	public void setShortCode(ShortCode shortCode) {
		this.shortCode = shortCode;
	}
	
}