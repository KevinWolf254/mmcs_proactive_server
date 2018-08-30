package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.TransactionCost;

public interface TransactionCostRepository extends JpaRepository<TransactionCost, Long> {

	public TransactionCost findByMessageId(String messageId);
}
