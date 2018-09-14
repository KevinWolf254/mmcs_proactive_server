package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.ATCharges;
import co.ke.proaktiv.io.pojos.helpers.Country;

public interface ATChargesRepository extends JpaRepository<ATCharges, Long> {

	public ATCharges findByCountry(Country country);
}
