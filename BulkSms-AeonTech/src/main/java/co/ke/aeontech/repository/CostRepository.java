package co.ke.aeontech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.Cost;
import co.ke.aeontech.pojos.helpers.CostType;

public interface CostRepository extends JpaRepository<Cost, Long> {

	public List<Cost> findByType(CostType type);
	
	public Cost findByCode(String code);
	
	public List<Cost> findByClient(Client client);
}
