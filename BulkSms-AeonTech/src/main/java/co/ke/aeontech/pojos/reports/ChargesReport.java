package co.ke.aeontech.pojos.reports;

public class ChargesReport extends Report {

	private double cost;

	public ChargesReport() {
		super();
	}

	public ChargesReport(int code, String title, 
			String message, double cost) {
		super(code, title, message);
		this.cost = cost;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
}
