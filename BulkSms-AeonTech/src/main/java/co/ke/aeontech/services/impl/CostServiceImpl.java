package co.ke.aeontech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Cost;
import co.ke.aeontech.repository.CostRepository;
import co.ke.aeontech.services.CostService;

@Service
public class CostServiceImpl implements CostService {

	@Autowired
	private CostRepository repository;
	
	@Override
	public Cost findByCode(String code) {
		return repository.findByCode(code);
	}
	@Override
	public Cost save(Cost cost) {
		final Cost saved = repository.save(cost);
		return saved;
	}

}
