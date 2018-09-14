package co.ke.proaktiv.io.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.proaktiv.io.pojos.helpers.Country;

@StaticMetamodel(Client.class)
public abstract class Client_ {

	public static volatile SingularAttribute<Client, Long> id;
	public static volatile SingularAttribute<Client, Country> country;
	public static volatile SingularAttribute<Client, String> name;	
	public static volatile SingularAttribute<Client, Boolean> isActivated;
	public static volatile SingularAttribute<Client, Date> createdOn;
	public static volatile SingularAttribute<Client, Set<ClientAdmin>> admins;
	public static volatile SingularAttribute<Client, Set<Confirmation>> paymentConfirmations;
	public static volatile SingularAttribute<Client, Set<Sale>> sales;
	public static volatile SingularAttribute<Client, Set<Cost>> saleCosts;
	public static volatile SingularAttribute<Client, Set<Token>> verificationTokens;

}