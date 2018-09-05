package co.ke.aeontech.pojos.reports;

import co.ke.aeontech.pojos.helpers.Response;

public class SuccessReport implements Response{

	private String title;
	private String message;
	
	public SuccessReport() {
		super();
	}
	public SuccessReport(String title, String message) {
		super();
		this.title = title;
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
