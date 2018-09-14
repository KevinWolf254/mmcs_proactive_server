package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.ProCharges;
import co.ke.proaktiv.io.pojos.helpers.Country;

public interface ProChargesRepository extends JpaRepository<ProCharges, Long> {

	public ProCharges findByCountry(Country country);
}
