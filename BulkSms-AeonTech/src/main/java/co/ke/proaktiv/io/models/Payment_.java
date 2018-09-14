package co.ke.proaktiv.io.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.proaktiv.io.pojos.helpers.Currency;
import co.ke.proaktiv.io.pojos.helpers.PaymentType;

@StaticMetamodel(Payment.class)
public abstract class Payment_ {

	public static volatile SingularAttribute<Payment, Long> id;
	public static volatile SingularAttribute<Payment, PaymentType> type;
	public static volatile SingularAttribute<Payment, String> account;
	public static volatile SingularAttribute<Payment, Currency> currency;
	public static volatile SingularAttribute<Payment, Double> amount;
}
