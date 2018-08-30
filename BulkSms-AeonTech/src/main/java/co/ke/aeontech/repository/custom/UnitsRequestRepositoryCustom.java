package co.ke.aeontech.repository.custom;

import java.util.List;

import co.ke.aeontech.models.UnitsRequest;

public interface UnitsRequestRepositoryCustom{

	public List<UnitsRequest> findPendingByOrganisationId(Long id);
}
