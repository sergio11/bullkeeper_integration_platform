package sanchez.sanchez.sergio.masoc.web.dto.request;

import java.util.Date;
import java.util.Locale;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.i18n.LocaleContextHolder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import sanchez.sanchez.sergio.masoc.persistence.constraints.FieldMatch;
import sanchez.sanchez.sergio.masoc.persistence.constraints.InDateRange;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ParentEmailShouldNotExist;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ValidPhoneNumber;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.masoc.web.rest.deserializers.BirthdayDeserializer;
import sanchez.sanchez.sergio.masoc.web.rest.deserializers.ClearStringDeserializer;
import sanchez.sanchez.sergio.masoc.web.rest.deserializers.PhoneNumberDeserializer;

@FieldMatch(first = "passwordClear", second = "confirmPassword", message = "{user.pass.not.match}")
public  class RegisterParentDTO {
	
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 3, max = 15, message = "{user.firstname.size}", groups = Extended.class)
	@JsonProperty("first_name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	protected String firstName;
	
	@NotBlank(message = "{user.lastname.notnull}")
    @Size(min = 3, max = 30, message = "{user.lastname.size}")
	@JsonProperty("last_name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	protected String lastName;
	
	
	@JsonDeserialize(using = BirthdayDeserializer.class)
	@InDateRange(min = "1-1-1960", max = "1-1-2000", message="{user.birthdate.invalid}")
	@JsonProperty("birthdate")
	protected Date birthdate;
 
    @NotBlank(message="{user.email.notnull}")
    @Email(message="{user.email.invalid}")
    @ParentEmailShouldNotExist(message="{user.email.unique}", groups = Extended.class)
    @JsonProperty("email")
    protected String email;
    
    @NotBlank(message="{user.pass.notnull}")
    @Size(min=8, max=25, message="{user.pass.size}")
	@JsonProperty("password_clear")
    protected String passwordClear;
    
    @NotBlank(message="{user.confirm.pass.notnull}")
    @JsonProperty("confirm_password")
    protected String confirmPassword;

	@ValidPhoneNumber(message = "{user.telephone.not.valid}")
	@JsonProperty("telephone")
	@JsonDeserialize(using = PhoneNumberDeserializer.class)
	protected PhoneNumber telephone;
	
	@JsonProperty("locale")
	protected Locale locale = LocaleContextHolder.getLocale();
	
    public RegisterParentDTO(){}


	public RegisterParentDTO(String firstName, String lastName, Date birthdate, String email, String passwordClear,
			String confirmPassword, PhoneNumber telephone, Locale locale) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
		this.passwordClear = passwordClear;
		this.confirmPassword = confirmPassword;
		this.telephone = telephone;
		this.locale = locale;
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


	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}


	@Override
	public String toString() {
		return "RegisterParentDTO [firstName=" + firstName + ", lastName=" + lastName + ", birthdate=" + birthdate
				+ ", email=" + email + ", passwordClear=" + passwordClear + ", confirmPassword=" + confirmPassword
				+ ", telephone=" + telephone + ", locale=" + locale + "]";
	}
	
}
