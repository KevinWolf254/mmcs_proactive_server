package co.ke.proaktiv.io.pojos.reports;

import co.ke.proaktiv.io.models.Cost;
import co.ke.proaktiv.io.models.Payment;
import co.ke.proaktiv.io.models.Sale;

public class SaleReport extends Report {

	private Sale sale;
	private Payment payment;
	private Cost cost;
	public SaleReport() {
		super();
	}
	public SaleReport(Sale sale, Payment payment, Cost cost) {
		super();
		this.sale = sale;
		this.payment = payment;
		this.cost = cost;
	}
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Cost getCost() {
		return cost;
	}
	public void setCost(Cost cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SaleReport [sale=").append(sale)
				.append(", payment=").append(payment)
				.append(", cost=").append(cost)
				.append("]");
		return builder.toString();
	}
}
