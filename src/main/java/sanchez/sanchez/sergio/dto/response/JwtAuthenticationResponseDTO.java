package sanchez.sanchez.sergio.dto.response;

public class JwtAuthenticationResponseDTO {

    private String token;
    
    public JwtAuthenticationResponseDTO(){}

    public JwtAuthenticationResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}
