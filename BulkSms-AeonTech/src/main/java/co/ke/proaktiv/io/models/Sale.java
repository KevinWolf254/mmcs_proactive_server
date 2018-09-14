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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.proaktiv.io.pojos.helpers.SaleType;
import co.ke.proaktiv.io.pojos.helpers.Status;

@JsonIdentityInfo
	(generator=ObjectIdGenerators.PropertyGenerator.class, 
		property="id")
@Entity
@Table(name="sale")
public class Sale {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	//this is the unique transaction code
	//could be mpesaTransNo or payPalTransNo
	@Column(name="code", unique=true, nullable = false)
	private String code;
	
	@Column(name="type", nullable = false)
	private SaleType type;
	
	@Column(name="status")
	private Status status;

	@Column(name="credit_disbursed")
	private boolean creditDisbursed;
	
	@Column(name="date")
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "client_id", nullable = true)
	private Client client;
	
	@OneToOne
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;
	
	public Sale() {
		super();
	}

	public Sale(String code, SaleType type, Status status, Payment payment) {
		super();
		this.code = code;
		this.type = type;
		this.status = status;
		this.creditDisbursed = Boolean.FALSE;
		this.payment = payment;
		this.date = new Date();
	}

	public Sale(String code, SaleType type,	Status status, Client client, 
			Payment payment) {
		super();
		this.code = code;
		this.type = type;
		this.status = status;
		this.client = client;
		this.creditDisbursed = Boolean.FALSE;
		this.payment = payment;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SaleType getType() {
		return type;
	}

	public void setType(SaleType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@JsonIgnore
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isCreditDisbursed() {
		return creditDisbursed;
	}

	public void setCreditDisbursed(boolean creditDisbursed) {
		this.creditDisbursed = creditDisbursed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Sale other = (Sale) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Sale [code=").append(code)
				.append(", type=").append(type)
				.append(", status=").append(status)
				.append(", creditDisbursed=").append(creditDisbursed)
				.append(", date=").append(date)
				.append("]");
		return builder.toString();
	}
}
