package sanchez.sanchez.sergio.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RegisterParentByFacebookDTO extends RegisterParentDTO {

	@JsonProperty("fb_access_token")
    private String fbAccessToken;
	
	@JsonProperty("fb_id")
    private String fbId;
	
	public RegisterParentByFacebookDTO(){}

	public RegisterParentByFacebookDTO(String fbAccessToken, String fbId) {
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
}
