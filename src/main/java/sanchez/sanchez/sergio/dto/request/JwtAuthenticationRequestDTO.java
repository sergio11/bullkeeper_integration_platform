package sanchez.sanchez.sergio.dto.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public final class JwtAuthenticationRequestDTO {
	
	public interface UsernameNotBlankCheck {}
	public interface UsernameSizeCheck {}
	public interface PasswordNotBlankCheck {}
	public interface PasswordSizeCheck {}
	@GroupSequence({ UsernameNotBlankCheck.class, UsernameSizeCheck.class, PasswordNotBlankCheck.class, PasswordSizeCheck.class })
	public interface OrderedChecks {}
	
	@NotBlank(message = "{analyst.username.notnull}", groups = UsernameNotBlankCheck.class)
    @Size(min = 5, max = 15, message = "{analyst.username.size}", groups = UsernameSizeCheck.class)
	private String email;
	@NotBlank(message="{analyst.pass.notnull}", groups = PasswordNotBlankCheck.class)
    @Size(min=8, max=25, message="{analyst.pass.size}", groups = PasswordSizeCheck.class)
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
