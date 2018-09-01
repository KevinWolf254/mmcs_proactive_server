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
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, 
property="id")
@Entity
@Table(name="transaction_cost")
public class TransactionCost {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="amount", nullable = false)
	private double amount;
	
	@Column(name="message_id", nullable = false)
	private String messageId;
	
	@Column(name="date", nullable = false)
	private Date date;
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Organisation organisation;
	
	public TransactionCost() {
		super();
	}
	public TransactionCost(double amount, String messageId, Organisation organisation) {
		super();
		this.amount = amount;
		this.messageId = messageId;
		this.organisation = organisation;
		this.date = new Date();
	}
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
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public Organisation getOrganisation() {
		return organisation;
	}
	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((organisation == null) ? 0 : organisation.hashCode());
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
		TransactionCost other = (TransactionCost) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (organisation == null) {
			if (other.organisation != null)
				return false;
		} else if (!organisation.equals(other.organisation))
			return false;
		return true;
	}
}
