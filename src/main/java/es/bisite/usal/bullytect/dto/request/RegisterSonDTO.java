package es.bisite.usal.bullytect.dto.request;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.bisite.usal.bullytect.persistence.constraints.SchoolMustExists;
import es.bisite.usal.bullytect.rest.deserializers.BirthdayDeserializer;

public final class RegisterSonDTO {
	
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
    private Date birthdate;
	
    @SchoolMustExists
    @JsonProperty("school")
    private String school;
    
    public RegisterSonDTO(){}

	public RegisterSonDTO(String firstName, String lastName, Date birthdate, String school) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.school = school;
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
    
}
