package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.models.Cost;

public interface CostService {

	public Cost findByCode(String code);

	public Cost save(Cost cost);
}
