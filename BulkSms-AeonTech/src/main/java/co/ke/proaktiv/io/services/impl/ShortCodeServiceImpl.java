package co.ke.proaktiv.io.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Sale;
import co.ke.proaktiv.io.models.ShortCode;
import co.ke.proaktiv.io.pojos.helpers.SaleType;
import co.ke.proaktiv.io.repository.ShortCodeRepository;
import co.ke.proaktiv.io.services.SaleService;
import co.ke.proaktiv.io.services.ShortCodeService;

@Service
public class ShortCodeServiceImpl implements ShortCodeService{

	@Autowired
	private ShortCodeRepository repository;
	@Autowired
	private SaleService saleService;

	@Override
	public Optional<ShortCode> findByName(final String name) {
		return repository.findByName(name);
	}
	
	@Override
	public ShortCode save(final ShortCode shortCode) {
		final ShortCode shortCode_ = repository.save(shortCode);
		log.info("##### saved: "+shortCode_);
		return shortCode_;
	}
	
	@Override
	public ShortCode findByClient(final Client client) {
		return repository.findByClient(client);
	}
	
	@Override
	public ShortCode findByClientId(final Long id) {
		return repository.findByClientId(id);
	}

	@Override
	public ShortCode findByClientName(final String name) {
		return repository.findByClientName(name);
	}
	
	@Override
	public Boolean exists(final String name) {
		final Optional<ShortCode> shortCode = findByName(name);
		if(!shortCode.isPresent())
			return false;
		return true;
	}	

	@Override
	public ShortCode confirm(final Client client, final Sale sale) {
		final ShortCode shortCode = repository.findByClient(client);
		shortCode.setPaid(Boolean.TRUE);
		final ShortCode shortCode_ = repository.save(shortCode);
		//update sale
		sale.setType(SaleType.SHORT_CODE);
		sale.setCreditDisbursed(Boolean.TRUE);
		saleService.save(sale);
		return shortCode_;
	}
	
	private static final Logger log = LoggerFactory.getLogger(ShortCodeServiceImpl.class);
}
