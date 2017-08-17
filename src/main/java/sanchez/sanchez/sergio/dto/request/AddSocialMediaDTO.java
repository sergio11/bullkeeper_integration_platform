package sanchez.sanchez.sergio.dto.request;

import org.hibernate.validator.constraints.NotBlank;

import sanchez.sanchez.sergio.persistence.constraints.SonShouldExists;
import sanchez.sanchez.sergio.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.persistence.constraints.ValidSocialMediaType;
import sanchez.sanchez.sergio.persistence.constraints.group.Extended;

public class AddSocialMediaDTO {
	
	@NotBlank(message = "{social.token.notnull}")
	private String accessToken;
	@ValidSocialMediaType(message = "{social.type.invalid}")
	private String type;
	@ValidObjectId(message = "{son.id.notvalid}")
	@SonShouldExists(message = "{social.son.not.exists}", groups = Extended.class)
	private String son;
	
	
	public AddSocialMediaDTO(String accessToken, String type, String son) {
		super();
		this.accessToken = accessToken;
		this.type = type;
		this.son = son;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getSon() {
		return son;
	}


	public void setSon(String son) {
		this.son = son;
	}
}
