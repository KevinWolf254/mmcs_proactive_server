package co.ke.aeontech.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.pojos.Reply;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="payment")
public class Payment {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="transaction_id", unique=true, nullable = false)
	private String transactionId;
	
	@Column(name="category")
	private String category;
	
	@Column(name="source")
	private String source;
	
	@Column(name="provider")
	private String provider;
	
	@Column(name="provider_ref_id", unique=true, nullable = false)
	private String providerRefId;
	
	@Column(name="currency")
	private Country currency;
	
	@Column(name="amount", nullable = false)
	private double amount;
	
	@Column(name="transaction_fee")
	private double transactionFee;
	
	@Column(name="provider_fee")
	private double providerFee;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="status")
	private Reply response;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Organisation organisation;

	public Payment() {
		super();
	}

	public Payment(String transactionId, String category, String source, 
			String provider, String providerRefId, Country currency, 
			double amount, double transactionFee, double providerFee, 
			Reply response) {
		super();
		this.transactionId = transactionId;
		this.category = category;
		this.source = source;
		this.provider = provider;
		this.providerRefId = providerRefId;
		this.currency = currency;
		this.amount = amount;
		this.transactionFee = transactionFee;
		this.providerFee = providerFee;
		this.response = response;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderRefId() {
		return providerRefId;
	}

	public void setProviderRefId(String providerRefId) {
		this.providerRefId = providerRefId;
	}

	public Country getCurrency() {
		return currency;
	}

	public void setCurrency(Country currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setValue(double amount) {
		this.amount = amount;
	}

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public double getProviderFee() {
		return providerFee;
	}

	public void setProviderFee(double providerFee) {
		this.providerFee = providerFee;
	}

	public Reply getResponse() {
		return response;
	}

	public void setResponse(Reply response) {
		this.response = response;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@JsonIgnore
	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	
	@Override
	public String toString() {		
		final StringBuilder builder = new StringBuilder();
		builder.append("Payment Notification [id=")
				.append(id)
				.append(", transactionId=").append(transactionId)
				.append(", category=").append(category)
				.append(", sourceType=").append(source)
				.append(", provider=").append(provider)
				.append(", providerRefId=").append(providerRefId)
				.append(", currency=").append(currency)
				.append(", value=").append(amount)
				.append(", transactionFee=").append(transactionFee)
				.append(", providerFee=").append(providerFee)
				.append(", response=").append(response)
				.append(", organisation=").append(organisation)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((providerRefId == null) ? 0 : providerRefId.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
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
		Payment other = (Payment) obj;
		if (providerRefId == null) {
			if (other.providerRefId != null)
				return false;
		} else if (!providerRefId.equals(other.providerRefId))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
	
}
