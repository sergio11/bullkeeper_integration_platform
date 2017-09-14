package es.bisite.usal.bullytect.service;

import es.bisite.usal.bullytect.dto.response.PasswordResetTokenDTO;

public interface IPasswordResetTokenService {
	PasswordResetTokenDTO createPasswordResetTokenForUser(String id); 
	Boolean isValid(String id, String token);
	PasswordResetTokenDTO getPasswordResetTokenForUser(String id);
	void deleteExpiredTokens();
}
