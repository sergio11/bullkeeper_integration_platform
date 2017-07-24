/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanchez.sanchez.sergio.persistence.entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sanchez.sanchez.sergio.persistence.utils.CascadeSave;

/**
 *
 * @author sergio
 */
@Document(collection="users")
public class UserEntity {
    
    @Id
    private ObjectId id;
    
    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;
    
    private Integer age;
    
    @DBRef
    @CascadeSave
    private List<SocialMediaEntity> socialMedia;

    @PersistenceConstructor
    public UserEntity(String firstName, String lastName, Integer age, List<SocialMediaEntity> socialMedia) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.socialMedia = socialMedia;
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

    public List<SocialMediaEntity> getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(List<SocialMediaEntity> socialMedia) {
        this.socialMedia = socialMedia;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", socialMedia=" + socialMedia + '}';
    }
    
    
   
}
