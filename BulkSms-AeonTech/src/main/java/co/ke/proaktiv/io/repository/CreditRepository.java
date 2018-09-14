package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long>{

	public Credit findByClient(Client client);
}
