package sanchez.sanchez.sergio.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.persistence.constraints.ParentEmailShouldNotExist;

public final class UpdateParentDTO {
	
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 5, max = 15, message = "{user.firstname.size}")
	@JsonProperty("first_name")
	private String firstName;
	@NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 5, max = 15, message = "{user.firstname.size}")
	@JsonProperty("last_name")
    private String lastName;
    private Integer age;
    @NotBlank(message="{user.email.notnull}")
    @Email(message="{user.email.invalid}")
    @ParentEmailShouldNotExist(message="{user.email.unique}")
    private String email;
    
    public UpdateParentDTO(){}
    
	public UpdateParentDTO(String firstName, String lastName, Integer age, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
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
}
