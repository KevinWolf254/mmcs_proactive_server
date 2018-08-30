package co.ke.aeontech.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.Country;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="organisation")
public class Organisation {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;	

	@Column(name="country")
	private Country country;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name = "enabled")
    private boolean enabled;
	
	@Column(name = "created_on")
    private Date createdOn;
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Administrator> administrators;
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<UnitsRequest> unitsRequests;
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Payment> payments;
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<TransactionCost> transCosts;
	
	public Organisation() {
		super();
		this.enabled = false;
	}

	public Organisation(Country country, String name) {
		super();
		this.country = country;
		this.name = name;
//		this.senderId = senderId;
		this.enabled = false;
		this.createdOn = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@JsonIgnore
	public List<Administrator> getAdministrators() {
		return administrators;
	}

	public void setAdministrators(List<Administrator> administrators) {
		this.administrators = administrators;
	}

	@JsonIgnore
	public List<UnitsRequest> getUnitsRequests() {
		return unitsRequests;
	}
	
	public void setUnitsRequests(List<UnitsRequest> unitsRequests) {
		this.unitsRequests = unitsRequests;
	}
	
	@JsonIgnore
	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public List<TransactionCost> getTransCosts() {
		return transCosts;
	}

	public void setTransCosts(List<TransactionCost> transCosts) {
		this.transCosts = transCosts;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organisation organisation = (Organisation) obj;
        if (!name.equals(organisation.name)) {
            return false;
        }
        return true;
    }
    
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Organisation [id=").append(id)
				.append(", country=").append(country)
				.append(", name=").append(name)
				.append(", enabled=").append(enabled)
				.append(", createdOn=").append(createdOn)
				.append("]");
		return builder.toString();
	}
	
}
