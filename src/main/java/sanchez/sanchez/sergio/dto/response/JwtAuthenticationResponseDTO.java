package sanchez.sanchez.sergio.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtAuthenticationResponseDTO {

	@JsonProperty("token")
    private String token;
    
    public JwtAuthenticationResponseDTO(){}
    
    public JwtAuthenticationResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}
