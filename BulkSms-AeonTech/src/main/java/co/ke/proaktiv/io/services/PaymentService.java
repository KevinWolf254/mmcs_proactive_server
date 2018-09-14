package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.models.Payment;

/**
 * 
 * @author kevin
 * @version 1.0.0
 * @since 2018
 */
public interface PaymentService {	
	/**
	 * saves payment
	 * @param payment
	 * @return Payment
	 */
	public Payment save(Payment payment);	
}
