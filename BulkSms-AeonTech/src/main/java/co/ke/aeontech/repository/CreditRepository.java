package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long>{

	public Credit findByClient(Client client);
}
