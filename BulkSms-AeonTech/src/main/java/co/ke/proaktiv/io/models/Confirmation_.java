package co.ke.proaktiv.io.models;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.proaktiv.io.pojos.helpers.State;

@StaticMetamodel(Confirmation.class)
public abstract class Confirmation_ {

	public static volatile SingularAttribute<Confirmation, Long> id;
	public static volatile SingularAttribute<Confirmation, Double> amount;
	public static volatile SingularAttribute<Confirmation, String> mpesaNo;
	public static volatile SingularAttribute<Confirmation, State> state;
	public static volatile SingularAttribute<Confirmation, Date> date;
	public static volatile SingularAttribute<Confirmation, Client> client;

}
