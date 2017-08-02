package sanchez.sanchez.sergio.persistence.entity;

import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */
@Document(collection = UserEntity.COLLECTION_NAME)
public class UserEntity {
    
    public final static String COLLECTION_NAME = "users";
    
    @Id
    private ObjectId id;
    
    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;
    
    private Integer age;

    @PersistenceConstructor
    public UserEntity(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public ObjectId getId() {
        return id;
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
    
    public String getFullName(){
        return this.firstName + this.lastName;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + '}';
    }
}
