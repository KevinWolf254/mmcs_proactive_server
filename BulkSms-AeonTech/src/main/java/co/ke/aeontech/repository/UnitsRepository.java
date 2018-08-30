package co.ke.aeontech.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Units;

public interface UnitsRepository extends JpaRepository<Units, Long>{

	@Transactional
	Units findByOrganisationId(Long id);

}
