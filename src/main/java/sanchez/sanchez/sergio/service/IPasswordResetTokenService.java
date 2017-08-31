package sanchez.sanchez.sergio.service;

import sanchez.sanchez.sergio.dto.response.PasswordResetTokenDTO;

public interface IPasswordResetTokenService {
	PasswordResetTokenDTO createPasswordResetTokenForUser(String id); 
	Boolean isValid(String id, String token);
}
