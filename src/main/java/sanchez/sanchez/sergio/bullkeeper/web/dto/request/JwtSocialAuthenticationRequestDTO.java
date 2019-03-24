package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class JwtSocialAuthenticationRequestDTO {

	@NotBlank(message = "{user.social.access.token.not.null}")
	@JsonProperty("token")
    private String token;
    
    public JwtSocialAuthenticationRequestDTO(){}

	public JwtSocialAuthenticationRequestDTO(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
