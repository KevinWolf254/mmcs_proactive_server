package co.ke.proaktiv.io.services.impl;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.ClientAdmin;
import co.ke.proaktiv.io.repository.ClientAdminRepository;
import co.ke.proaktiv.io.services.ClientAdminService;

@Service
public class ClientAdminServiceImpl implements ClientAdminService{
	@Autowired
	private ClientAdminRepository repository;

	@Override
	public Optional<ClientAdmin>  findByEmail(final String adminEmail) {
		return repository.findByEmail(adminEmail);
	}

	@Override
	public Set<ClientAdmin> findByClient(final Client client) {
		return repository.findByClient(client);
	}

	@Override
	public Set<ClientAdmin> findByClientId(final Long id) {
		return repository.findByClientId(id);
	}
	
	@Override
	public ClientAdmin save(final ClientAdmin admin) {
		final ClientAdmin admin_ = repository.save(admin);
		log.info("###### saved: "+admin_);
		return admin_;
	}

	@Override
	public void delete(final ClientAdmin admin) {
		repository.delete(admin);		
	}
	private static final Logger log = LoggerFactory.getLogger(ClientAdminServiceImpl.class);
}
