package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.UnitsRequest;
import co.ke.aeontech.repository.custom.UnitsRequestRepositoryCustom;

public interface UnitsRequestRepository extends JpaRepository<UnitsRequest, Long>, UnitsRequestRepositoryCustom {

	public UnitsRequest findByMpesaTransNo(final String mpesaTransNo);

}
