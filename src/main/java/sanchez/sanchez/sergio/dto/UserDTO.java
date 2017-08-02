package sanchez.sanchez.sergio.dto;

import java.io.Serializable;

/**
 *
 * @author sergio
 */
public class UserDTO implements Serializable {
    
    private String firstName;
    private String lastName;
    private Integer age;

    public UserDTO(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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
