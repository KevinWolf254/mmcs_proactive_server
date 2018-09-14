package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.ExchangeRate;
import co.ke.proaktiv.io.pojos.helpers.Country;

public interface ExchangeRatesRepository extends JpaRepository<ExchangeRate, Long>{

	public ExchangeRate findByCountry(Country country);

}
