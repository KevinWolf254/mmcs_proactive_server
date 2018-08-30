package co.ke.aeontech.models;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.pojos.Reply;

@StaticMetamodel(Payment.class)
public abstract class Payment_ {

	public static volatile SingularAttribute<Payment, Long> id;
	
	public static volatile SingularAttribute<Payment, String> transactionId;	
	
	public static volatile SingularAttribute<Payment, String> category;
	
	public static volatile SingularAttribute<Payment, String> source;
	
	public static volatile SingularAttribute<Payment, String> provider;
	
	public static volatile SingularAttribute<Payment, Country> currency;
	
	public static volatile SingularAttribute<Payment, Double> amount;
	
	public static volatile SingularAttribute<Payment, Double> transactionFee;
	
	public static volatile SingularAttribute<Payment, Double> providerFee;
	
	public static volatile SingularAttribute<Payment, Date> date;
	
	public static volatile SingularAttribute<Payment, Reply> response;
	
	public static volatile SingularAttribute<Payment, Organisation> organisation;
}