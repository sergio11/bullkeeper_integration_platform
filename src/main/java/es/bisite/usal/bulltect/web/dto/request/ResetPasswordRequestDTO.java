package es.bisite.usal.bulltect.web.dto.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.bisite.usal.bulltect.persistence.constraints.ParentAccountShouldActive;
import es.bisite.usal.bulltect.persistence.constraints.ParentAccountShouldNotLocked;
import es.bisite.usal.bulltect.persistence.constraints.ParentEmailShouldExist;

public final class ResetPasswordRequestDTO {

	@NotBlank(message = "{user.email.notnull}")
	@Email(message="{user.email.invalid}")
	@ParentEmailShouldExist(message="{user.email.not.exists}")
	@ParentAccountShouldActive(message="{user.email.not.activate}")
	@ParentAccountShouldNotLocked(message="{user.email.not.locked}")
	@JsonProperty("email")
	private String email;
    
    public ResetPasswordRequestDTO(){}

	public ResetPasswordRequestDTO(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
