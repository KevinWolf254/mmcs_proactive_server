package co.ke.proaktiv.io.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Token;
import co.ke.proaktiv.io.repository.TokenRepository;
import co.ke.proaktiv.io.services.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
    private TokenRepository tokenRepository;
	
	@Override
	public void createToken(final Client organisation, final String token) {
		final Token myToken = new Token(token, organisation);
        tokenRepository.save(myToken);		
	}
	
	 @Override
	 public Token getToken(final String VerificationToken) {
        return tokenRepository.findByValue(VerificationToken);
     }

	@Override
	public Token recreateToken(String existingVerificationToken) {
		Token vToken = tokenRepository.findByValue(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
	}

	@Async
	@Override
	public void delete(Token verificationToken) {
		tokenRepository.delete(verificationToken);
	}

}
