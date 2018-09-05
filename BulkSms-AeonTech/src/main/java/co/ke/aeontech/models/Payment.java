package co.ke.aeontech.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.helpers.Currency;
import co.ke.aeontech.pojos.helpers.PaymentType;

@JsonIdentityInfo
	(generator=ObjectIdGenerators.PropertyGenerator.class,
		property="id")
@Entity
@Table(name="payment")
public class Payment {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="type")
	private PaymentType type;
	
	@Column(name="account")
	//paymentSource -phoneNo or payPalAcnt_no
	private String account;
	
	@Column(name="currency")
	private Currency currency;

	@Column(name="amount", nullable = false)
	private double amount;
	
	public Payment() {
		super();
	}

	public Payment(PaymentType type, String account, 
			Currency currency, double amount) {
		super();
		this.type = type;
		this.account = account;
		this.currency = currency;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Payment [type=").append(type)
				.append(", account=").append(account)
				.append(", currency=").append(currency)
				.append(", amount=").append(amount)
				.append("]");
		return builder.toString();
	}
}
