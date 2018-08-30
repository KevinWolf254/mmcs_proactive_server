package co.ke.aeontech.services.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Payment;
import co.ke.aeontech.models.UnitsRequest;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.pojos.PaymentNotification;
import co.ke.aeontech.pojos.Reply;
import co.ke.aeontech.pojos.Request;
import co.ke.aeontech.pojos.UnitsResponse;
import co.ke.aeontech.repository.PaymentRepository;
import co.ke.aeontech.services.PaymentService;
import co.ke.aeontech.services.UnitsRequestService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository repository;
	
	@Autowired
	private UnitsRequestService requestService;
	
	@Override
	public Payment save(Payment newPayment) {
		final Payment payment = repository.save(newPayment);
		log.info("Confirmed: "+payment);
		return payment;
	}

	/*
	 *@param notification
	 *received from africa's talking API for payments made to
	 *_aeon buy goods and services till number 
	 */
	@Override
	public Payment processNotification(PaymentNotification notification) {
		String value = notification.getValue();
		String currency_code = value.substring(0, 3);		
		double amount = Double.parseDouble(value.substring(4).trim());
		double transaction_fee = Double.parseDouble(notification.getTransactionFee()
				.substring(4).trim());

		double provider_fee = Double.parseDouble(notification.getTransactionFee()
				.substring(4).trim());

		Country currency = getCurrency(currency_code);

		Reply status = getNotificationStatus(notification.getStatus());

		return save(new Payment(notification.getTransactionId(), notification.getCategory(), 
				notification.getSource(), notification.getProvider(), notification.getProviderRefId(), 
				currency, amount, transaction_fee, provider_fee, status));
	}

	/**
	 * @param notificationStatus
	 */
	private Reply getNotificationStatus(String notificationStatus) {
		Reply status;
		if(notificationStatus.equals(Reply.SUCCESS.getStatus()))
			status = Reply.SUCCESS;
		else
			status = Reply.FAILED;
		return status;
	}

	/**
	 * @param currency_code
	 */
	private Country getCurrency(String currency_code) {
		Country currency = null;
		
		if(currency_code.equals(Country.RWANDA.getAcronym())) {
			currency = Country.RWANDA;
		}else if(currency_code.equals(Country.KENYA.getAcronym())){
			currency = Country.KENYA;
		}else if(currency_code.equals(Country.TANZANIA.getAcronym())){
			currency = Country.TANZANIA;
		}else if(currency_code.equals(Country.UGANDA.getAcronym())){
			currency = Country.UGANDA;
		}
		
		return currency;
	}

	
	@Override
	public UnitsResponse confirmPayment(Organisation organisation, String mpesaTransNo, 
			double requestedAmount) {
		
		final Payment payment = findByProviderRefId(mpesaTransNo);
		final UnitsRequest request = requestService.findByMpesaTransNo(mpesaTransNo);
		
		if(payment == null)
			return new UnitsResponse(Reply.FAILED, "notification of payment may not have been received");
		if(payment.getResponse().equals(Reply.FAILED))
			return new UnitsResponse(Reply.FAILED, "payment may have failed, try again");
		if(payment.getAmount() != requestedAmount)
			return new UnitsResponse(Reply.FAILED, "payments do not match. Kindy check the amount paid.");
		if(payment.getOrganisation() != null) {
			if(payment.getOrganisation().equals(organisation)) {
				if(request.getRequestStatus().equals(Request.PENDING))
					return new UnitsResponse(Reply.FAILED, "top up has been confirmed; "
							+ "awaiting to be added to your account.");
				else 
					return new UnitsResponse(Reply.FAILED, "top up has been confirmed. "
							+ "top up amount was added to your account.");
			}
			return new UnitsResponse(Reply.FAILED, "Invalid request");
		}
		
		payment.setOrganisation(organisation);
		save(payment);
		
		return new UnitsResponse(Reply.SUCCESS, "payment is valid");
	}

	@Override
	public Payment findByProviderRefId(String mpesaTransNo) {
		return repository.findByProviderRefId(mpesaTransNo);
	}

	@Override
	public List<Payment> findByOrganisationId(Long id) {
		return repository.findByOrganisationId(id);
	}

	@Override
	public List<Payment> SearchBtw(Date from, Date to, Long id) {
		return repository.SearchBtw(from, to, id);
	}
	private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

}
