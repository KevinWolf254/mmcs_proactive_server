package co.ke.aeontech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Payment;
import co.ke.aeontech.repository.custom.PaymentRepositoryCustom;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom{

	public Payment findByProviderRefId(String mpesaTransNo);

	public List<Payment> findByOrganisationId(Long id);

}
