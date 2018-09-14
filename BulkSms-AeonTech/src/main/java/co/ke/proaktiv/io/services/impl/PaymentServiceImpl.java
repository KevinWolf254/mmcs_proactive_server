package co.ke.proaktiv.io.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Payment;
import co.ke.proaktiv.io.repository.PaymentRepository;
import co.ke.proaktiv.io.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository repository;
	
	@Override
	public Payment save(Payment payment) {
		return repository.save(payment);
	}

}
