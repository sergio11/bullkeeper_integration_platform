package sanchez.sanchez.sergio.dto.response;

import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author sergio
 */
public class UserDTO extends ResourceSupport implements Serializable {
    
    private String identity;
    private String firstName;
    private String lastName;
    private Integer age;
    
    public UserDTO(){}

    public UserDTO(String identity, String firstName, String lastName, Integer age) {
        this.identity = identity;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + '}';
    }
}
