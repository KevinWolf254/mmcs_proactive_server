package co.ke.proaktiv.io.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Cost;
import co.ke.proaktiv.io.repository.CostRepository;
import co.ke.proaktiv.io.services.CostService;

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
