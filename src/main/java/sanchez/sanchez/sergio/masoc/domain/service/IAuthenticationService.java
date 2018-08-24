package sanchez.sanchez.sergio.masoc.domain.service;

import org.springframework.mobile.device.Device;
import sanchez.sanchez.sergio.masoc.web.dto.response.JwtAuthenticationResponseDTO;

/**
 * Authentication Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface IAuthenticationService {
	
	/**
	 * Create Authentication Token For Parent
	 * @param username
	 * @param password
	 * @param device
	 * @return
	 */
    JwtAuthenticationResponseDTO createAuthenticationTokenForParent(final String username, final String password, final Device device);
    
    /**
     * Create Authentication Token For Admin
     * @param username
     * @param password
     * @param device
     * @return
     */
    JwtAuthenticationResponseDTO createAuthenticationTokenForAdmin(final String username, final String password, final Device device);
    
}
