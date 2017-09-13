package sanchez.sanchez.sergio.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class JwtAuthenticationRequestDTO {

	@NotBlank(message = "{user.email.notnull}")
	@Email(message="{user.email.invalid}")
	@JsonProperty("email")
	private String email;
	@NotBlank(message="{user.pass.notnull}")
    @Size(min=8, max=25, message="{user.pass.size}")
	@JsonProperty("password")
	private String password;
    
    public JwtAuthenticationRequestDTO(){}

    public JwtAuthenticationRequestDTO(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

   
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
