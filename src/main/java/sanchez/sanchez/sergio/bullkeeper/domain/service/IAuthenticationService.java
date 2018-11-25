package sanchez.sanchez.sergio.bullkeeper.domain.service;

import org.springframework.mobile.device.Device;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.JwtAuthenticationResponseDTO;

/**
 * Authentication Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface IAuthenticationService {
	
	/**
	 * Create Authentication Token For Guardian
	 * @param username
	 * @param password
	 * @param device
	 * @return
	 */
    JwtAuthenticationResponseDTO createAuthenticationTokenForGuardian(final String username, final String password, final Device device);
    
    /**
     * Create Authentication Token For Admin
     * @param username
     * @param password
     * @param device
     * @return
     */
    JwtAuthenticationResponseDTO createAuthenticationTokenForAdmin(final String username, final String password, final Device device);
    
}
