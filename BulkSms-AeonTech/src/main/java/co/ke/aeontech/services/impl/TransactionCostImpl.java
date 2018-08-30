package co.ke.aeontech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.TransactionCost;
import co.ke.aeontech.repository.TransactionCostRepository;
import co.ke.aeontech.services.TransactionCostService;

@Service
public class TransactionCostImpl implements TransactionCostService {

	@Autowired
	private TransactionCostRepository transRepository;
	@Override
	public TransactionCost findByMessageId(String messageId) {
		return transRepository.findByMessageId(messageId);
	}
	@Override
	public TransactionCost save(TransactionCost transactionCost) {
		return transRepository.save(transactionCost);
	}

}
