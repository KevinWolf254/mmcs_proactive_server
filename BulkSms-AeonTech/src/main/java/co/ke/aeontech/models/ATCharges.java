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
@Table(name="at_charges")
public class ATCharges {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="country", nullable = false, unique = true)
	private Country country;
	
	@Column(name="rwf", nullable = false)
	private double rwf;
	
	@Column(name="rwf_airtel", nullable = false)
	private double rwfAir;
	
	@Column(name="kes", nullable = false)
	private double kes;
	
	@Column(name="kes_airtel", nullable = false)
	private double kesAir;
	
	@Column(name="tzs", nullable = false)
	private double tzs;
	
	@Column(name="tzs_airtel", nullable = false)
	private double tzsAir;
	
	@Column(name="ugx", nullable = false)
	private double ugx;
	
	@Column(name="ugx_airtel", nullable = false)
	private double ugxAir;
	
	@Column(name="other", nullable = false)
	private double other;
	
	public ATCharges() {
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
	public double getOther() {
		return other;
	}
	public void setOther(double dollar) {
		this.other = dollar;
	}
	public double getKesAir() {
		return kesAir;
	}
	public void setKesAir(double kesAir) {
		this.kesAir = kesAir;
	}
	public double getUgxAir() {
		return ugxAir;
	}
	public void setUgxAir(double ugxAir) {
		this.ugxAir = ugxAir;
	}
	public double getRwfAir() {
		return rwfAir;
	}
	public void setRwfAir(double rwfAir) {
		this.rwfAir = rwfAir;
	}
	public double getTzsAir() {
		return tzsAir;
	}
	public void setTzsAir(double tzsAir) {
		this.tzsAir = tzsAir;
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
		ATCharges other = (ATCharges) obj;
		if (country != other.country)
			return false;
		return true;
	}
	
}
