package co.ke.aeontech.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.helpers.Currency;

@JsonIdentityInfo
	(generator=ObjectIdGenerators.PropertyGenerator.class, 
		property="id")
@Entity
@Table(name="credit")
public class Credit {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="currency", nullable = false)
	private Currency currency;
	
	@Column(name="amount", nullable = false)
	private double amount;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	public Credit() {
		super();
	}

	
	public Credit(Currency currency, Client client) {
		super();
		this.currency = currency;
		this.amount = 0.00;
		this.client = client;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client organisation) {
		this.client = organisation;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Credit [currency=")
				.append(currency)
				.append(", amount=")
				.append(amount)
				.append(", client")
				.append(client)
				.append("]");
		return builder.toString();
	}
	
}
