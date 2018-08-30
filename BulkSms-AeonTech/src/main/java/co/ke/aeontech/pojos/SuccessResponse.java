package co.ke.aeontech.pojos;

public class SuccessResponse implements Response{

	private String title;
	private String message;
	
	public SuccessResponse() {
		super();
	}
	public SuccessResponse(String title, String message) {
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
