package co.ke.aeontech.services;

import co.ke.aeontech.models.TransactionCost;

public interface TransactionCostService {

	public TransactionCost findByMessageId(String messageId);

	public TransactionCost save(TransactionCost transactionCost);
}
