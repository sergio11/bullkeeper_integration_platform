package sanchez.sanchez.sergio.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import sanchez.sanchez.sergio.persistence.constraints.SchoolMustExists;

public final class RegisterSonDTO {
	
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 5, max = 15, message = "{user.firstname.size}")
	private String firstName;
	@NotBlank(message = "{user.lastname.notnull}")
    @Size(min = 5, max = 15, message = "{user.lastname.size}")
    private String lastName;
    private Integer age;
    @SchoolMustExists
    private String school;
    
    public RegisterSonDTO(){}

	public RegisterSonDTO(String firstName, String lastName, Integer age, String school) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
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
    
}
