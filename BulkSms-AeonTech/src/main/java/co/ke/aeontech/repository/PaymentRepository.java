package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
