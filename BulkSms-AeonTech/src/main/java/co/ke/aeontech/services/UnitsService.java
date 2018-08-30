package co.ke.aeontech.services;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Units;
import co.ke.aeontech.pojos.MpesaConfirmation;
import co.ke.aeontech.pojos.UnitsDetailsResponse;
import co.ke.aeontech.pojos.UnitsResponse;

public interface UnitsService {
	
	public void save(final Units units);
	
	public UnitsDetailsResponse getUnitsInfo(final String email);

	public UnitsDetailsResponse getUnitsInfoById(final Long id);
	
	public Units findByOrganisationId(final Long id);
	
	public UnitsResponse confirmPayment(final Organisation organisation, final MpesaConfirmation request);

	public void delete(final Units units);
}
