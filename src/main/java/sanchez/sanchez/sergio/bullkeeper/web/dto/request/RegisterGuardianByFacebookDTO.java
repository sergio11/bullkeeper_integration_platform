package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class RegisterGuardianByFacebookDTO extends RegisterGuardianDTO {

	/**
	 * 
	 */
	@JsonProperty("fb_access_token")
    private String fbAccessToken;
	
	/**
	 * 
	 */
	@JsonProperty("fb_id")
    private String fbId;
	
	public RegisterGuardianByFacebookDTO(){}

	public RegisterGuardianByFacebookDTO(String fbAccessToken, String fbId) {
		super();
		this.fbAccessToken = fbAccessToken;
		this.fbId = fbId;
	}

	public String getFbAccessToken() {
		return fbAccessToken;
	}

	public void setFbAccessToken(String fbAccessToken) {
		this.fbAccessToken = fbAccessToken;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	@Override
	public String toString() {
		return "RegisterGuardianByFacebookDTO [fbAccessToken=" + fbAccessToken + ", fbId=" + fbId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", birthdate=" + birthdate + ", email=" + email
				+ ", passwordClear=" + passwordClear + ", confirmPassword=" + confirmPassword + ", telephone="
				+ telephone + "]";
	}
	
	
}
