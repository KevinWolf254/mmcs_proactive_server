package co.ke.proaktiv.io.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

import co.ke.proaktiv.io.pojos.helpers.Country;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="client")
public class Client {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;	

	@Column(name="country")
	private Country country;
	
	@Column(name="name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "enabled")
    private boolean enabled;
	
	@Column(name = "created_on")
    private Date createdOn;
	
	@OneToMany(mappedBy = "client", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ClientAdmin> admins = new HashSet<ClientAdmin>();

	@OneToMany(mappedBy = "client", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Sale> sales = new HashSet<Sale>();
	
	@OneToMany(mappedBy = "client", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Cost> costs = new HashSet<Cost>();
	
	@OneToMany(mappedBy = "client", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Token> tokens = new HashSet<Token>();
	
	public Client() {
		super();
	}

	public Client(Country country, String name) {
		super();
		this.country = country;
		this.name = name;
		this.enabled = Boolean.FALSE;
		this.createdOn = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@JsonIgnore
	public Set<ClientAdmin> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<ClientAdmin> admins) {
		this.admins = admins;
	}
	
	@JsonIgnore
	public Set<Sale> getSales() {
		return sales;
	}

	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}

	@JsonIgnore
	public Set<Cost> getCosts() {
		return costs;
	}

	public void setCosts(Set<Cost> costs) {
		this.costs = costs;
	}

	@JsonIgnore
	public Set<Token> getTokens() {
		return tokens;
	}

	public void setTokens(Set<Token> tokens) {
		this.tokens = tokens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Client [id=").append(id)
				.append(", country=").append(country)
				.append(", name=").append(name)
				.append(", enabled=").append(enabled)
				.append(", createdOn=").append(createdOn)
				.append("]");
		return builder.toString();
	}
	
}
