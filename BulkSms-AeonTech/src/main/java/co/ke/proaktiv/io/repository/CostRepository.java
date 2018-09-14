package co.ke.proaktiv.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Cost;
import co.ke.proaktiv.io.pojos.helpers.CostType;

public interface CostRepository extends JpaRepository<Cost, Long> {

	public List<Cost> findByType(CostType type);
	
	public Cost findByCode(String code);
	
	public List<Cost> findByClient(Client client);
}
