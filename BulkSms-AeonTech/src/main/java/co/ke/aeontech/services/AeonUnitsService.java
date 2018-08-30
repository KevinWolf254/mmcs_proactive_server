package co.ke.aeontech.services;

import java.math.BigDecimal;

import co.ke.aeontech.models.AeonUnits;
import co.ke.aeontech.pojos.Country;

public interface AeonUnitsService {

	public AeonUnits findById(final Long aeonId);

	public void save(final AeonUnits aeon);

	public BigDecimal convertAeonUnitsTo(final Country org_currency);

	public void updateAeonUnits(final double remaining_aeon_units);

	public BigDecimal convertAeonUnitsFrom(final Country org_currency, final BigDecimal converted_units);

	public void subtractAeonUnits(BigDecimal cost);

}
