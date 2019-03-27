package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.FieldMatch;


/**
 * 
 * @author ssanchez
 *
 */
@FieldMatch(first = "passwordClear", second = "confirmPassword", message = "{user.pass.not.match}")
public final class ChangePasswordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Password Clear
     */
    @NotBlank(message="{user.pass.not.null}")
    @Size(min=8, max=25, message="{user.pass.size}")
	@JsonProperty("password_clear")
    protected String passwordClear;
    
    
    /**
     * Confirm Password
     */
    @NotBlank(message="{user.confirm.pass.not.null}")
    @JsonProperty("confirm_password")
    protected String confirmPassword;
    
    public ChangePasswordDTO() {}

    /**
     * 
     * @param passwordClear
     * @param confirmPassword
     */
	public ChangePasswordDTO(final String passwordClear, final String confirmPassword) {
		super();
		this.passwordClear = passwordClear;
		this.confirmPassword = confirmPassword;
	}

	public String getPasswordClear() {
		return passwordClear;
	}

	public void setPasswordClear(String passwordClear) {
		this.passwordClear = passwordClear;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordDTO [passwordClear=" + passwordClear + ", confirmPassword=" + confirmPassword + "]";
	}
    

}
