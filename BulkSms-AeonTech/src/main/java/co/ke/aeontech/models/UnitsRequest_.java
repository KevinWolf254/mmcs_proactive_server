package co.ke.aeontech.models;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.aeontech.pojos.Request;

@StaticMetamodel(UnitsRequest.class)
public abstract class UnitsRequest_ {

	public static volatile SingularAttribute<UnitsRequest, Long> id;
	public static volatile SingularAttribute<UnitsRequest, String> amountRequested;
	public static volatile SingularAttribute<UnitsRequest, String> mpesaTransNo;
	public static volatile SingularAttribute<UnitsRequest, Date> requestDate;
	public static volatile SingularAttribute<UnitsRequest, Request> requestStatus;
	public static volatile SingularAttribute<UnitsRequest, Organisation> organisation;

}
