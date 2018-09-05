package co.ke.aeontech.services;

import co.ke.aeontech.models.Client;
import co.ke.aeontech.models.Token;

public interface TokenService {
	
	public void createToken(final Client client, final String token);

	public Token recreateToken(String existingVerificationToken);
	
	public Token getToken(final String VerificationToken);

	public void delete(Token verificationToken);

}
