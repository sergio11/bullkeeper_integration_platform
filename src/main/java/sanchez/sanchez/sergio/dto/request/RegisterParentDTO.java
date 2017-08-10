package sanchez.sanchez.sergio.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.persistence.constraints.FieldMatch;
import sanchez.sanchez.sergio.persistence.constraints.UserEmailUnique;

@FieldMatch(first = "passwordClear", second = "confirmPassword", message = "{user.pass.not.match}")
public final class RegisterParentDTO {
	
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 5, max = 15, message = "{user.firstname.size}")
	private String firstName;
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 5, max = 15, message = "{user.firstname.size}")
    private String lastName;
    private Integer age;
    @NotBlank(message="{user.email.notnull}")
    @Email(message="{user.email.invalid}")
    @UserEmailUnique(message="{user.email.unique}")
    private String email;
    
    @NotBlank(message="{parent.pass.notnull}")
    @Size(min=8, max=25, message="{parent.pass.size}")
	@JsonProperty("password_clear")
    private String passwordClear;
    
    @NotBlank(message="{user.confirm.pass.notnull}")
    @JsonProperty("confirm_password")
    private String confirmPassword;
    
    public RegisterParentDTO(){}

	public RegisterParentDTO(String firstName, String lastName, Integer age, String email, String passwordClear,
			String confirmPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.passwordClear = passwordClear;
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
