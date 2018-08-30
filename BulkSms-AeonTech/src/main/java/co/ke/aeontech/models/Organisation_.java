package co.ke.aeontech.models;

import java.util.Date;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.aeontech.pojos.Country;

@StaticMetamodel(Organisation.class)
public abstract class Organisation_ {

	public static volatile SingularAttribute<Organisation, Long> id;
	public static volatile SingularAttribute<Organisation, String> name;	
	public static volatile SingularAttribute<Organisation, Boolean> enabled;
	public static volatile SingularAttribute<Organisation, Country> country;
	public static volatile SingularAttribute<Organisation, String> senderId;
	public static volatile SingularAttribute<Organisation, List<Administrator>> administrators;
	public static volatile SingularAttribute<Organisation, List<UnitsRequest>> unitsRequests;
	public static volatile SingularAttribute<Organisation, List<Payment>> payments;
	public static volatile SingularAttribute<Organisation, Date> createdOn;

}