package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.ATCharges;
import co.ke.aeontech.pojos.helpers.Country;

public interface ATChargesRepository extends JpaRepository<ATCharges, Long> {

	public ATCharges findByCountry(Country country);
}
