package co.ke.aeontech.pojos.reports;

public class SmsDeliveryReport extends Report{

	private String messageId;
	private int received;
	private int rejected;
	private String rejectedNos;
	
	public SmsDeliveryReport() {
		super();
	}

//	public SmsDeliveryReport(String messageId, int received, int rejected, String rejectedNos) {
//		super();
//		this.messageId = messageId;
//		this.received = received;
//		this.rejected = rejected;
//		this.rejectedNos = rejectedNos;
//	}

	public SmsDeliveryReport(int code, String title, String message, 
			String messageId, int received, int rejected,
			String rejectedNos) {
		super(code, title, message);
		this.messageId = messageId;
		this.received = received;
		this.rejected = rejected;
		this.rejectedNos = rejectedNos;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getReceived() {
		return received;
	}

	public void setReceived(int received) {
		this.received = received;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

	public String getRejectedNos() {
		return rejectedNos;
	}

	public void setRejectedNos(String rejectedNos) {
		this.rejectedNos = rejectedNos;
	}
}
