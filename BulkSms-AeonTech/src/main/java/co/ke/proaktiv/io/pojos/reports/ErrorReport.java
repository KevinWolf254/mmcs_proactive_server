package co.ke.proaktiv.io.pojos.reports;

import co.ke.proaktiv.io.pojos.helpers.Response;

public class ErrorReport implements Response{

	private String error;
	private String error_description;
	
	public ErrorReport() {
		super();
	}
	public ErrorReport(String error, String error_description) {
		super();
		this.error = error;
		this.error_description = error_description;
	}
	public void setError(String error) {
		this.error = error;
	}
	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
	public String getError() {
		return error;
	}
	public String getError_description() {
		return error_description;
	}
	
}
