package sanchez.sanchez.sergio.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class JwtFacebookAuthenticationRequestDTO {

	@NotBlank(message = "{user.fbid.notnull}")
	@JsonProperty("id")
	private String id;
	@NotBlank(message = "{user.fbAccessToken.notnull}")
	@JsonProperty("token")
    private String token;
    
    public JwtFacebookAuthenticationRequestDTO(){}

	public JwtFacebookAuthenticationRequestDTO(String id, String token) {
		super();
		this.id = id;
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
