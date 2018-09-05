package co.ke.aeontech.models;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.helpers.State;

@JsonIdentityInfo
	(generator=ObjectIdGenerators.PropertyGenerator.class, 
		property="id")
@Entity
@Table(name="confirmation")
public class Confirmation {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;	
	
	@Column(name="amount", nullable = false)
	private double amount;
	
	@Column(name="mpesa_no", nullable = false, unique = true)
	private String mpesaNo;
	
	@Column(name="state", nullable = false)
	private State state;
	
	@Column(name="request_date")
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	public Confirmation() {
		super();
	}

	public Confirmation(double amount, String mpesaNo, 
			Client client) {
		super();
		this.amount = amount;
		this.mpesaNo = mpesaNo;
		this.date = new Date();
		this.state = State.PENDING;
		this.client = client;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMpesaNo() {
		return mpesaNo;
	}

	public void setMpesaNo(String mpesaNo) {
		this.mpesaNo = mpesaNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date requestDate) {
		this.date = requestDate;
	}

	@JsonIgnore
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mpesaNo == null) ? 0 : mpesaNo.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		Confirmation other = (Confirmation) obj;
		if (mpesaNo == null) {
			if (other.mpesaNo != null)
				return false;
		} else if (!mpesaNo.equals(other.mpesaNo))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UnitsRequest [id=").append(id)
				.append(", amount=").append(amount)
				.append(", mpesaNo=").append(mpesaNo)
				.append(", date=").append(date)
				.append(", state=").append(state)
				.append("]");
		return builder.toString();
	}
}
