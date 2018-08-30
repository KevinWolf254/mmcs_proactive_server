package co.ke.aeontech.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="org_units")
public class Units {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="units_available")
	private double unitsAvailable;
	
	@OneToOne
	@JoinColumn(name = "org_id")
	private Organisation organisation;
	
	public Units() {
		super();
	}

	public Units(Organisation organisation) {
		super();
		this.unitsAvailable = 0.00;
		this.organisation = organisation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getUnitsAvailable() {
		return unitsAvailable;
	}

	public void setUnitsAvailable(double unitsAvailable) {
		this.unitsAvailable = unitsAvailable;
	}

	public Organisation getClient() {
		return organisation;
	}

	public void setClient(Organisation organisation) {
		this.organisation = organisation;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Units [id=")
				.append(id)
				.append(", unitsAvailable=")
				.append(unitsAvailable)
				.append(", organisation")
				.append(organisation)
				.append("]");
		return builder.toString();
	}
	
}
