package co.ke.aeontech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Payment;
import co.ke.aeontech.repository.PaymentRepository;
import co.ke.aeontech.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository repository;
	
	@Override
	public Payment save(Payment payment) {
		return repository.save(payment);
	}

}
