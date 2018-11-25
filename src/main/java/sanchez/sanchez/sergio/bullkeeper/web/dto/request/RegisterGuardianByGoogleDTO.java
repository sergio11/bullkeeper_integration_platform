package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class RegisterGuardianByGoogleDTO extends RegisterGuardianDTO {
	
	@JsonProperty("google_id")
    private String googleId;
	
	@JsonProperty("picture")
    private String picture;
	
	public RegisterGuardianByGoogleDTO(){}

	public RegisterGuardianByGoogleDTO(String googleId, String picture) {
		super();
		this.googleId = googleId;
		this.picture = picture;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "RegisterGuardianByGoogleDTO [googleId=" + googleId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", birthdate=" + birthdate + ", email=" + email + ", passwordClear=" + passwordClear
				+ ", confirmPassword=" + confirmPassword + ", telephone=" + telephone + ", locale=" + locale + "]";
	}

	
	
}
