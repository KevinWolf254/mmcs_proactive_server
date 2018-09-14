package co.ke.proaktiv.io.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.proaktiv.io.pojos.helpers.CostType;
import co.ke.proaktiv.io.pojos.helpers.Currency;

@JsonIdentityInfo
	(generator=ObjectIdGenerators.PropertyGenerator.class, 
		property="id")
@Entity
@Table(name="sms_cost")
public class Cost {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="type", nullable = false)
	private CostType type;
	
	@Column(name="currency", nullable = false)
	private Currency currency;
	
	@Column(name="amount", nullable = false)
	private double amount;
	
	//can be a messageId or mpesaNo
	@Column(name="code", nullable = false)
	private String code;
	
	@Column(name="incurred_on", nullable = false)
	private Date incurredOn;
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "client_id")
	private Client client;
	
	public Cost() {
		super();
	}
	public Cost(CostType type, Currency currency, double amount, 
			String code, Client client) {
		super();
		this.type = type;
		this.currency = currency;
		this.amount = amount;
		this.code = code;
		this.client = client;
		this.incurredOn = new Date();
	}
	
	public Cost(CostType type, Currency currency, double amount, 
			String code) {
		super();
		this.type = type;
		this.currency = currency;
		this.amount = amount;
		this.code = code;
		this.incurredOn = new Date();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CostType getType() {
		return type;
	}
	public void setType(CostType type) {
		this.type = type;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getIncurredOn() {
		return incurredOn;
	}
	public void setIncurredOn(Date incurredOn) {
		this.incurredOn = incurredOn;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cost other = (Cost) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		return true;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SaleCost [currency=").append(currency)
				.append(", amount=").append(amount)
				.append(", type=").append(type)
				.append(", messageId=").append(code)
				.append(", date=").append(incurredOn)
				.append(", client=").append(client)
				.append("]");
		return builder.toString();
	}
}
