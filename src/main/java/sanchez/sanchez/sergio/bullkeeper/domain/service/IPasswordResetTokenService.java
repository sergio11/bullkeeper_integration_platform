package sanchez.sanchez.sergio.bullkeeper.domain.service;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.PasswordResetTokenDTO;

/**
 * Password Reset Token Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface IPasswordResetTokenService {
	
	/**
	 * Create Password Reset Token For User
	 * @param id
	 * @return
	 */
	PasswordResetTokenDTO createPasswordResetTokenForUser(final String id); 
	
	/**
	 * Is Valid
	 * @param id
	 * @param token
	 * @return
	 */
	Boolean isValid(final String id, final String token);
	
	/**
	 * Get Password Reset Token For User
	 * @param id
	 * @return
	 */
	PasswordResetTokenDTO getPasswordResetTokenForUser(final String id);
	
	/**
	 * Delete Expired Tokes
	 */
	void deleteExpiredTokens();
}
