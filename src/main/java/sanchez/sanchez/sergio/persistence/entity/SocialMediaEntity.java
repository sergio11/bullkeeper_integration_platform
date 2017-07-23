/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanchez.sanchez.sergio.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */
@Document(collection="social_media")
public class SocialMediaEntity {
    
    @Id
    private ObjectId id;
    
    @Field("access_token")
    private String accessToken;

    public ObjectId getId() {
        return id;
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "SocialMediaEntity{" + "id=" + id + ", accessToken=" + accessToken + '}';
    }
    
    
    
}
