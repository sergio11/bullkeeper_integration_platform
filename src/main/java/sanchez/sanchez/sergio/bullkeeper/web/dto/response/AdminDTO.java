package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sergio
 */
public class AdminDTO extends ResourceSupport implements Serializable {
    
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
    
    public AdminDTO(){}


    public AdminDTO(String identity, String firstName, String lastName, String birthdate, Integer age) {
		super();
		this.identity = identity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.age = age;
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
    
    public String getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}


	public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


	@Override
	public String toString() {
		return "AdminDTO [identity=" + identity + ", firstName=" + firstName + ", lastName=" + lastName + ", birthdate="
				+ birthdate + ", age=" + age + "]";
	}
}
