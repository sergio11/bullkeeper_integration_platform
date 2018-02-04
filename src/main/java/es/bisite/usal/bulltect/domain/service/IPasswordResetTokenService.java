package es.bisite.usal.bulltect.domain.service;

import es.bisite.usal.bulltect.web.dto.response.PasswordResetTokenDTO;

public interface IPasswordResetTokenService {
	PasswordResetTokenDTO createPasswordResetTokenForUser(String id); 
	Boolean isValid(String id, String token);
	PasswordResetTokenDTO getPasswordResetTokenForUser(String id);
	void deleteExpiredTokens();
}
