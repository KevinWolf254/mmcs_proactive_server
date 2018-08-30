package co.ke.aeontech.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.VerificationToken;
import co.ke.aeontech.repository.VerificationTokenRepository;
import co.ke.aeontech.services.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Autowired
    private VerificationTokenRepository tokenRepository;
	
	@Override
	public void createToken(final Organisation organisation, final String token) {
		final VerificationToken myToken = new VerificationToken(token, organisation);
        tokenRepository.save(myToken);		
	}
	
	 @Override
	 public VerificationToken getToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
     }

	@Override
	public VerificationToken recreateToken(String existingVerificationToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
	}

	@Async
	@Override
	public void delete(VerificationToken verificationToken) {
		tokenRepository.delete(verificationToken);
	}

}
