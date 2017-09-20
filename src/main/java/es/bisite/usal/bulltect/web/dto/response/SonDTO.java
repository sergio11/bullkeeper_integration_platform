package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SonDTO extends ResourceSupport implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("identity")
	private String identity;
	@JsonProperty("first_name")
    private String firstName;
	@JsonProperty("last_name")
    private String lastName;
	@JsonProperty("birthdate")
    private String birthdate;
	@JsonProperty("age")
    private Integer age;
	@JsonProperty("school")
    private String school;
    
    public SonDTO(){}
   

	public SonDTO(String identity, String firstName, String lastName, String birthdate, Integer age, String school) {
		super();
		this.identity = identity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.age = age;
		this.school = school;
	}



	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}


	@Override
	public String toString() {
		return "SonDTO [identity=" + identity + ", firstName=" + firstName + ", lastName=" + lastName + ", birthdate="
				+ birthdate + ", age=" + age + ", school=" + school + "]";
	}
	
	
}
