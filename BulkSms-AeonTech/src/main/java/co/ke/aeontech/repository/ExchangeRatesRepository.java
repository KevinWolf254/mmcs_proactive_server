package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.ExchangeRate;
import co.ke.aeontech.pojos.Country;

public interface ExchangeRatesRepository extends JpaRepository<ExchangeRate, Long>{

	public ExchangeRate findByCountry(Country country);

}
