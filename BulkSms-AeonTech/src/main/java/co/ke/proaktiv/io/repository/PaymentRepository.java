package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
