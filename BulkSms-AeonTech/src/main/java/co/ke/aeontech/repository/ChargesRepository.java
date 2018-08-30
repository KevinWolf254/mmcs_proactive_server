package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Charges;
import co.ke.aeontech.pojos.Country;

public interface ChargesRepository extends JpaRepository<Charges, Long> {

	public Charges findByCountry(Country country);
}
