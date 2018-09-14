package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.models.Client;
import co.ke.proaktiv.io.models.Token;

public interface TokenService {
	
	public void createToken(final Client client, final String token);

	public Token recreateToken(String existingVerificationToken);
	
	public Token getToken(final String VerificationToken);

	public void delete(Token verificationToken);

}
