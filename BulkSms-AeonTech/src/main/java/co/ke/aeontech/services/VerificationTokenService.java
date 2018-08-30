package co.ke.aeontech.services;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.VerificationToken;

public interface VerificationTokenService {
	
	public void createToken(final Organisation organisation, final String token);

	public VerificationToken recreateToken(String existingVerificationToken);
	
	public VerificationToken getToken(final String VerificationToken);

	public void delete(VerificationToken verificationToken);

}
