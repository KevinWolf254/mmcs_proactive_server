package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.ProCharges;
import co.ke.aeontech.pojos.helpers.Country;

public interface ProChargesRepository extends JpaRepository<ProCharges, Long> {

	public ProCharges findByCountry(Country country);
}
