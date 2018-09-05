package co.ke.aeontech.services;

import co.ke.aeontech.models.Cost;

public interface CostService {

	public Cost findByCode(String code);

	public Cost save(Cost cost);
}
