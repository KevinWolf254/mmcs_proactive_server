package co.ke.aeontech.services;

import java.math.BigDecimal;
import java.util.List;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.UnitsRequest;

public interface UnitsRequestService {

	public UnitsRequest findByMpesaTransNo(final String mpesaTransNo);
	
	public List<UnitsRequest> findPendingByOrganisationId(final Long id);

	public void save(final UnitsRequest requested);

	public BigDecimal calculatePendingAmounts(final Organisation organisation, 
			final List<UnitsRequest> pending_requests);

	public void updatePendingUnits(final List<UnitsRequest> pending_requests);

}
