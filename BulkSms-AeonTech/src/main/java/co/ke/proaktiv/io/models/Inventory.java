package co.ke.proaktiv.io.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.proaktiv.io.pojos.helpers.Currency;

@JsonIdentityInfo
	(generator=ObjectIdGenerators.PropertyGenerator.class, 
		property="id")
@Entity
@Table(name="inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="currency")
	private Currency currency;
	
	@Column(name="amount")
	private double amount;

	public Inventory() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		builder.append("Inventory [currency=").append(currency)
				.append(" amount=").append(amount)
				.append("]");
		return builder.toString();
	}
}
