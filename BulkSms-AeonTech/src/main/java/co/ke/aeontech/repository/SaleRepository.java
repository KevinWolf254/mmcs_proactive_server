package co.ke.aeontech.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.Payment;
import co.ke.aeontech.models.Sale;
import co.ke.aeontech.pojos.helpers.SaleType;
import co.ke.aeontech.repository.custom.SaleRepositoryCustom;

public interface SaleRepository extends JpaRepository<Sale, Long>, SaleRepositoryCustom{
	
	public Sale findByType(SaleType type);
	
	public List<Sale> findByDate(Date date);

	public Sale findByPayment(Payment payment);

	public List<Sale> findByClient(Client client);
	
	public List<Sale> findByClientId(Long id);

	public Optional<Sale> findByCode(String code);
}
