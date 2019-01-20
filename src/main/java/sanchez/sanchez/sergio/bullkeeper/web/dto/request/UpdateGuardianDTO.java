package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.InAgeRange;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.NewParentEmailShouldNotExist;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidPhoneNumber;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.BirthdayDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.PhoneNumberDeserializer;

/**
 * Update Parent DTO
 * @author sergiosanchezsanchez
 *
 */
public final class UpdateGuardianDTO {
	
	/**
	 * First Name
	 */
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 3, max = 15, message = "{user.firstname.size}", groups = Extended.class)
	@JsonProperty("first_name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	private String firstName;
	
	/**
	 * Last Name
	 */
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 3, max = 15, message = "{user.firstname.size}", groups = Extended.class)
	@JsonProperty("last_name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
    private String lastName;
	
	/**
	 * Birthdate
	 */
	@InAgeRange(min="18", max="90", message="{user.age.invalid}", groups = Extended.class)
	@JsonProperty("birthdate")
	@JsonDeserialize(using = BirthdayDeserializer.class)
    private Date birthdate;
	
	/**
	 * Email
	 */
    @NotBlank(message="{user.email.notnull}")
    @Email(message="{user.email.invalid}")
    @NewParentEmailShouldNotExist(message="{user.email.unique}", groups = Extended.class)
    private String email;
    
    /**
     * Telephone
     */
    @ValidPhoneNumber(message = "{user.telephone.not.valid}")
	@JsonProperty("telephone")
	@JsonDeserialize(using = PhoneNumberDeserializer.class)
	private PhoneNumber telephone;
    
    /**
     * Visible
     */
    @JsonProperty("visible")
    private boolean visible;
    
    public UpdateGuardianDTO(){}
   
    /**
     * 
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param email
     * @param telephone
     * @param visible
     */
	public UpdateGuardianDTO(String firstName, String lastName, Date birthdate, 
			String email, PhoneNumber telephone, boolean visible) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
		this.telephone = telephone;
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

	public PhoneNumber getTelephone() {
		return telephone;
	}

	public void setTelephone(PhoneNumber telephone) {
		this.telephone = telephone;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
