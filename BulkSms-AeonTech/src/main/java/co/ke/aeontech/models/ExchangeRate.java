package co.ke.aeontech.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.helpers.Country;

@JsonIdentityInfo
	(generator=ObjectIdGenerators.PropertyGenerator.class, 
		property="id")
@Entity
@Table(name="exchange_rates")
public class ExchangeRate {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="country", nullable = false, unique = true)
	private Country country;
	
	@Column(name="rwf", nullable = false)
	private double rwf;
	
	@Column(name="kes", nullable = false)
	private double kes;
	
	@Column(name="tzs", nullable = false)
	private double tzs;
	
	@Column(name="ugx", nullable = false)
	private double ugx;
	
	@Column(name="dollar", nullable = false)
	private double dollar;
	
	public ExchangeRate() {
		super();
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
	public double getRwf() {
		return rwf;
	}
	public void setRwf(double rwf) {
		this.rwf = rwf;
	}
	public double getKes() {
		return kes;
	}
	public void setKes(double kes) {
		this.kes = kes;
	}
	public double getTzs() {
		return tzs;
	}
	public void setTzs(double tzs) {
		this.tzs = tzs;
	}
	public double getUgx() {
		return ugx;
	}
	public void setUgx(double ugx) {
		this.ugx = ugx;
	}
	public double getDollar() {
		return dollar;
	}
	public void setDollar(double dollar) {
		this.dollar = dollar;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
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
		ExchangeRate other = (ExchangeRate) obj;
		if (country != other.country)
			return false;
		return true;
	}
	
}
