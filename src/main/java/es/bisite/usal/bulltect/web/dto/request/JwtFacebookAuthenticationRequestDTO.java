package es.bisite.usal.bulltect.web.dto.request;

import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class JwtFacebookAuthenticationRequestDTO {

	@NotBlank(message = "{user.fbAccessToken.notnull}")
	@JsonProperty("token")
    private String token;
    
    public JwtFacebookAuthenticationRequestDTO(){}

	public JwtFacebookAuthenticationRequestDTO(String token) {
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
