package co.ke.aeontech.services;

import java.util.Date;
import java.util.List;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Payment;
import co.ke.aeontech.pojos.PaymentNotification;
import co.ke.aeontech.pojos.UnitsResponse;

public interface PaymentService {	
	
	public List<Payment> findByOrganisationId(Long id);

	public Payment findByProviderRefId(String mpesaTransNo);

	public Payment save(Payment newPayment);

	public Payment processNotification(PaymentNotification notification);

	public UnitsResponse confirmPayment(Organisation client, String mpesaTransNo, 
			double requestedAmount);

	public List<Payment> SearchBtw(Date from, Date to, Long id);
}
