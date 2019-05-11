package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.util.Date;
import java.util.Locale;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.i18n.LocaleContextHolder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.FieldMatch;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.InAgeRange;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ParentEmailShouldNotExist;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidPhoneNumber;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.BirthdayDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.PhoneNumberDeserializer;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@FieldMatch(first = "passwordClear", second = "confirmPassword", message = "{user.pass.not.match}")
public  class RegisterGuardianDTO {
	
	/**
	 * First Name
	 */
	@NotBlank(message = "{user.firstname.not.null}")
    @Size(min = 3, max = 15, message = "{user.firstname.size}", groups = Extended.class)
	@JsonProperty("first_name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	protected String firstName;
	
	
	/**
	 * Last Name
	 */
	@NotBlank(message = "{user.lastname.not.null}")
    @Size(min = 3, max = 30, message = "{user.lastname.size}")
	@JsonProperty("last_name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	protected String lastName;
	
	/**
	 * Birth Date
	 */
	@JsonDeserialize(using = BirthdayDeserializer.class)
	@InAgeRange(min="18", max="90", message="{user.age.invalid}", groups = Extended.class)
	@JsonProperty("birthdate")
	protected Date birthdate;
 
	/**
	 * Email
	 */
    @NotBlank(message="{user.email.not.null}")
    @Email(message="{user.email.invalid}")
    @ParentEmailShouldNotExist(message="{user.email.unique}", groups = Extended.class)
    @JsonProperty("email")
    protected String email;
    
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

    
    /**
     * Telephone
     */
	@ValidPhoneNumber(message = "{user.telephone.not.valid}")
	@JsonProperty("telephone")
	@JsonDeserialize(using = PhoneNumberDeserializer.class)
	protected PhoneNumber telephone;
	
	
	/**
	 * Visible
	 */
	@JsonProperty("visible")
	protected boolean visible;
	
	
	/**
	 * Locale
	 */
	@JsonProperty("locale")
	protected Locale locale = LocaleContextHolder.getLocale();
	
	/**
	 * 
	 */
    public RegisterGuardianDTO(){}

    /**
     * 
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param email
     * @param passwordClear
     * @param confirmPassword
     * @param telephone
     * @param locale
     * @param visible
     */
	public RegisterGuardianDTO(String firstName, String lastName, Date birthdate, String email, String passwordClear,
			String confirmPassword, PhoneNumber telephone, Locale locale,
			boolean visible) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
		this.passwordClear = passwordClear;
		this.confirmPassword = confirmPassword;
		this.telephone = telephone;
		this.locale = locale;
		this.visible = visible;
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
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String toString() {
		return "RegisterGuardianDTO [firstName=" + firstName + ", lastName=" + lastName + ", birthdate=" + birthdate
				+ ", email=" + email + ", passwordClear=" + passwordClear + ", confirmPassword=" + confirmPassword
				+ ", telephone=" + telephone + ", locale=" + locale + "]";
	}
	
}
