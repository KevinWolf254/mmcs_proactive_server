package co.ke.aeontech.models;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.aeontech.pojos.helpers.SaleType;
import co.ke.aeontech.pojos.helpers.Status;

@StaticMetamodel(Sale.class)
public abstract class Sale_ {

	public static volatile SingularAttribute<Sale, Long> id;
	
	public static volatile SingularAttribute<Sale, String> code;
	
	public static volatile SingularAttribute<Sale, SaleType> type;
	
	public static volatile SingularAttribute<Sale, Status> status;
	
	public static volatile SingularAttribute<Sale, Boolean> confirmed;

	public static volatile SingularAttribute<Sale, Date> date;

	public static volatile SingularAttribute<Sale, Client> client;
	
	public static volatile SingularAttribute<Sale, Payment> payment;
}