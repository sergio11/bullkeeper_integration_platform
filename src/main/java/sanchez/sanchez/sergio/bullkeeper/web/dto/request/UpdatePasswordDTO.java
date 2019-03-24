package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.FieldMatch;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.FieldNotMatch;

@FieldMatch(first = "passwordClear", second = "confirmPassword", message = "{guardian.pass.not.match}")
@FieldNotMatch(first = "currentClearPassword", second = "passwordClear", message = "{guardian.current.pass.not.match}" )
public final class UpdatePasswordDTO {
	
	@NotBlank(message="{guardian.current.password.not.null}")
    @Size(min=8, max=25, message="{guardian.current.password.size}")
	@JsonProperty("current_clear_password")
    private String currentClearPassword;
	
	@NotBlank(message="{guardian.new.pass.not.null}")
    @Size(min=8, max=25, message="{guardian.new.pass.size}")
	@JsonProperty("password_clear")
    private String passwordClear;
    
    @NotBlank(message="{guardian.new.pass.confirm.not.null}")
    @JsonProperty("confirm_password")
    private String confirmPassword;
    
    public UpdatePasswordDTO(){}

	public UpdatePasswordDTO(String currentClearPassword, String passwordClear, String confirmPassword) {
		super();
		this.currentClearPassword = currentClearPassword;
		this.passwordClear = passwordClear;
		this.confirmPassword = confirmPassword;
	}

	public String getCurrentClearPassword() {
		return currentClearPassword;
	}

	public void setCurrentClearPassword(String currentClearPassword) {
		this.currentClearPassword = currentClearPassword;
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
}
