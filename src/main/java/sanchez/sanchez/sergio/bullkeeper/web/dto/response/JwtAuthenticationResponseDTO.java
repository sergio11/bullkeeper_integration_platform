package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class JwtAuthenticationResponseDTO {

	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Token
	 */
	@JsonProperty("token")
    private String token;

	
	public JwtAuthenticationResponseDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param token
	 */
	public JwtAuthenticationResponseDTO(String identity, String token) {
		super();
		this.identity = identity;
		this.token = token;
	}

	public String getIdentity() {
		return identity;
	}

	public String getToken() {
		return token;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationResponseDTO [identity=" + identity + ", token=" + token + "]";
	}

}
