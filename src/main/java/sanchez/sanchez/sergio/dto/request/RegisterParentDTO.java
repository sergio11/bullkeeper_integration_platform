package sanchez.sanchez.sergio.dto.request;

import java.util.Date;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import sanchez.sanchez.sergio.persistence.constraints.FieldMatch;
import sanchez.sanchez.sergio.persistence.constraints.InDateRange;
import sanchez.sanchez.sergio.persistence.constraints.ParentEmailShouldNotExist;
import sanchez.sanchez.sergio.persistence.constraints.ValidPhoneNumber;
import sanchez.sanchez.sergio.rest.deserializers.BirthdayDeserializer;
import sanchez.sanchez.sergio.rest.deserializers.PhoneNumberDeserializer;

@FieldMatch(first = "passwordClear", second = "confirmPassword", message = "{user.pass.not.match}")
public final class RegisterParentDTO {
	
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 5, max = 15, message = "{user.firstname.size}")
	@JsonProperty("first_name")
	private String firstName;
	@NotBlank(message = "{user.lastname.notnull}")
    @Size(min = 5, max = 15, message = "{user.lastname.size}")
	@JsonProperty("last_name")
    private String lastName;
	
	@JsonProperty("birthdate")
	@JsonDeserialize(using = BirthdayDeserializer.class)
	@InDateRange(min = "1960-1-1", max = "2000-1-1", message="{user.birthdate.invalid}")
    private Date birthdate;
 
    @NotBlank(message="{user.email.notnull}")
    @Email(message="{user.email.invalid}")
    @ParentEmailShouldNotExist(message="{user.email.unique}")
    private String email;
    
    @NotBlank(message="{user.pass.notnull}")
    @Size(min=8, max=25, message="{user.pass.size}")
	@JsonProperty("password_clear")
    private String passwordClear;
    
    @NotBlank(message="{user.confirm.pass.notnull}")
    @JsonProperty("confirm_password")
    private String confirmPassword;

	@ValidPhoneNumber(message = "user.telephone.not.valid")
	@JsonProperty("telephone")
	@JsonDeserialize(using = PhoneNumberDeserializer.class)
	private PhoneNumber telephone;
    
    public RegisterParentDTO(){}


	public RegisterParentDTO(String firstName, String lastName, Date birthdate, String email, String passwordClear,
			String confirmPassword, PhoneNumber telephone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
		this.passwordClear = passwordClear;
		this.confirmPassword = confirmPassword;
		this.telephone = telephone;
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

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
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
	
	public PhoneNumber getTelephone() {
		return telephone;
	}

	public void setTelephone(PhoneNumber telephone) {
		this.telephone = telephone;
	}
	
}
