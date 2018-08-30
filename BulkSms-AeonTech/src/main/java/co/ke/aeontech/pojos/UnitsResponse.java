package co.ke.aeontech.pojos;

public class UnitsResponse implements Response{

	private Reply status;
	private String message;
	public UnitsResponse() {
		super();
	}
	public UnitsResponse(Reply status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public Reply getStatus() {
		return status;
	}
	public void setStatus(Reply status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
