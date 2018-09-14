package co.ke.proaktiv.io.models;

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
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo
	(generator = ObjectIdGenerators.PropertyGenerator.class, 
		property="id")
@Entity
@Table(name="client_admin")
public class ClientAdmin {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	public ClientAdmin() {
		super();
	}

	public ClientAdmin(String email) {
		super();
		this.email = email;
	}

	public ClientAdmin(String email, String phoneNo) {
		super();
		this.email = email;
		this.phoneNo = phoneNo;
	}

	public ClientAdmin(String email, String phoneNo, Client client) {
		super();
		this.email = email;
		this.phoneNo = phoneNo;
		this.client = client;
	}

	public ClientAdmin(String email, Client client) {
		super();
		this.email = email;
		this.client = client;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {		
		final StringBuilder builder = new StringBuilder();
		builder.append("ClientAdmin [email=")
				.append(email)
				.append("]");
		return builder.toString();
	}	
}
