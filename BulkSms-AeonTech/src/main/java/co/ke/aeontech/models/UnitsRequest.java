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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.Request;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="units_request")
public class UnitsRequest {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;	
	
	@Column(name="amount_requested", nullable = false)
	private double amountRequested;
	
	@Column(name="mpesa_trans_no", nullable = false, unique = true)
	private String mpesaTransNo;
	
	@Column(name="request_date", nullable = false)
	private Date requestDate;
	
	@Column(name="request_status", nullable = false)
	private Request requestStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="org_units_requests",
				inverseJoinColumns=@JoinColumn(name = "org_fk"),
				joinColumns=@JoinColumn(name = "request_fk"))
	private Organisation organisation;

	public UnitsRequest() {
		super();
	}

	public UnitsRequest(double amountRequested, String mpesaTransNo, 
			Organisation organisation) {
		super();
		this.id = 0L;
		this.amountRequested = amountRequested;
		this.mpesaTransNo = mpesaTransNo;
		this.requestDate = new Date();
		this.requestStatus = Request.PENDING;
		this.organisation = organisation;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmountRequested() {
		return amountRequested;
	}

	public void setAmountRequested(double amountRequested) {
		this.amountRequested = amountRequested;
	}

	public String getMpesaTransNo() {
		return mpesaTransNo;
	}

	public void setMpesaTransNo(String mpesaTransNo) {
		this.mpesaTransNo = mpesaTransNo;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@JsonIgnore
	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	public Request getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(Request requestStatus) {
		this.requestStatus = requestStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mpesaTransNo == null) ? 0 : mpesaTransNo.hashCode());
		result = prime * result + ((requestDate == null) ? 0 : requestDate.hashCode());
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
		UnitsRequest other = (UnitsRequest) obj;
		if (mpesaTransNo == null) {
			if (other.mpesaTransNo != null)
				return false;
		} else if (!mpesaTransNo.equals(other.mpesaTransNo))
			return false;
		if (requestDate == null) {
			if (other.requestDate != null)
				return false;
		} else if (!requestDate.equals(other.requestDate))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UnitsRequest [id=").append(id)
				.append(", amountRequested=").append(amountRequested)
				.append(", mpesaTransNo=").append(mpesaTransNo)
				.append(", requestDate=").append(requestDate)
				.append(", requestStatus=").append(requestStatus)
				.append("]");
		return builder.toString();
	}
}
