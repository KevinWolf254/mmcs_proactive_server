package co.ke.proaktiv.io.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Payment;
import co.ke.proaktiv.io.models.Sale;
import co.ke.proaktiv.io.pojos.helpers.SaleType;
import co.ke.proaktiv.io.repository.custom.SaleRepositoryCustom;

public interface SaleRepository extends JpaRepository<Sale, Long>, SaleRepositoryCustom{
	
	public Sale findByType(SaleType type);
	
	public List<Sale> findByDate(Date date);

	public Sale findByPayment(Payment payment);

	public List<Sale> findByClient(Client client);
	
	public List<Sale> findByClientId(Long id);

	public Optional<Sale> findByCode(String code);
}
