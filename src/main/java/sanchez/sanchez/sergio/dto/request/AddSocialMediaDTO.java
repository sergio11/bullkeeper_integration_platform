package sanchez.sanchez.sergio.dto.request;

import org.hibernate.validator.constraints.NotBlank;

public class AddSocialMediaDTO {
	
	@NotBlank(message = "{social.token.notnull}")
	private String accessToken;
	@NotBlank(message = "{social.type.notnull}")
	private String type;
	@NotBlank(message = "{social.user.notnull}")
	private String user;
	
	
	public AddSocialMediaDTO(String accessToken, String type, String user) {
		super();
		this.accessToken = accessToken;
		this.type = type;
		this.user = user;
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


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}
}
