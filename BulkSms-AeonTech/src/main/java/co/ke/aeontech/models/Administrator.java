package co.ke.aeontech.models;

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
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.Country;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="administrator")
public class Administrator {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="country_code")
	private Country countryCode;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="email", nullable = false)
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="org_administrators",
				inverseJoinColumns=@JoinColumn(name = "org_fk"),
				joinColumns=@JoinColumn(name = "admin_fk"))
	private Organisation organisation;

	public Administrator() {
		super();
	}

	public Administrator(String email) {
		super();
		this.email = email;
	}

	public Administrator(String email, Country countryCode, String phoneNo) {
		super();
		this.email = email;
		this.countryCode = countryCode;
		this.phoneNo = phoneNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	public Country getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Country countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {		
		final StringBuilder builder = new StringBuilder();
		builder.append("Administrator [id=")
				.append(id)
				.append(", countryCode=")
				.append(countryCode.getCountryCode())
				.append(", phoneNo=")
				.append(phoneNo)
				.append(", email=")
				.append(email)
				.append(", organisation=")
				.append(organisation)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((phoneNo == null) ? 0 : phoneNo.hashCode());
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
		Administrator other = (Administrator) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (phoneNo == null) {
			if (other.phoneNo != null)
				return false;
		} else if (!phoneNo.equals(other.phoneNo))
			return false;
		return true;
	}
	
}
