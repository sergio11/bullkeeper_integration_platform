package es.bisite.usal.bulltect.web.dto.request;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import es.bisite.usal.bulltect.persistence.constraints.InDateRange;
import es.bisite.usal.bulltect.persistence.constraints.NewParentEmailShouldNotExist;
import es.bisite.usal.bulltect.persistence.constraints.SonShouldExists;
import es.bisite.usal.bulltect.persistence.constraints.ValidObjectId;
import es.bisite.usal.bulltect.persistence.constraints.ValidPhoneNumber;
import es.bisite.usal.bulltect.persistence.constraints.group.Extended;
import es.bisite.usal.bulltect.web.rest.deserializers.BirthdayDeserializer;
import es.bisite.usal.bulltect.web.rest.deserializers.PhoneNumberDeserializer;
import es.bisite.usal.bulltect.web.rest.deserializers.ClearStringDeserializer;

public final class UpdateParentDTO {
	
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 3, max = 15, message = "{user.firstname.size}", groups = Extended.class)
	@JsonProperty("first_name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	private String firstName;
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 3, max = 15, message = "{user.firstname.size}", groups = Extended.class)
	@JsonProperty("last_name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
    private String lastName;
	@InDateRange(min = "1-1-1960", max = "1-1-2000", message="{user.birthdate.invalid}")
	@JsonProperty("birthdate")
	@JsonDeserialize(using = BirthdayDeserializer.class)
    private Date birthdate;
	
    @NotBlank(message="{user.email.notnull}")
    @Email(message="{user.email.invalid}")
    @NewParentEmailShouldNotExist(message="{user.email.unique}", groups = Extended.class)
    private String email;
    
    @ValidPhoneNumber(message = "{user.telephone.not.valid}")
	@JsonProperty("telephone")
	@JsonDeserialize(using = PhoneNumberDeserializer.class)
	private PhoneNumber telephone;
    
    public UpdateParentDTO(){}
   
	public UpdateParentDTO(String firstName, String lastName, Date birthdate, String email, PhoneNumber telephone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
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

	public PhoneNumber getTelephone() {
		return telephone;
	}

	public void setTelephone(PhoneNumber telephone) {
		this.telephone = telephone;
	}
    
	
}
