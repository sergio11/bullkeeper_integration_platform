package sanchez.sanchez.sergio.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */
@Document(collection = SocialMediaEntity.COLLECTION_NAME)
public class SocialMediaEntity {
    
    public final static String COLLECTION_NAME = "social_media";
    
    @Id
    private ObjectId id;
    
    @Field("access_token")
    private String accessToken;
    
    @Field("social_media_type")
    private SocialMediaTypeEnum type;
    
    @Field("invalid_token")
    private Boolean invalidToken = Boolean.FALSE;
    
    @DBRef
    private SonEntity sonEntity;

    @PersistenceConstructor
    public SocialMediaEntity(String accessToken, SocialMediaTypeEnum type, SonEntity sonEntity) {
        this.accessToken = accessToken;
        this.type = type;
        this.sonEntity = sonEntity;
    }
    
    public ObjectId getId() {
        return id;
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public SocialMediaTypeEnum getType() {
        return type;
    }

    public void setType(SocialMediaTypeEnum type) {
        this.type = type;
    }

    public Boolean isInvalidToken() {
        return invalidToken;
    }

    public void setInvalidToken(Boolean invalidToken) {
        this.invalidToken = invalidToken;
    }

	public SonEntity getSonEntity() {
		return sonEntity;
	}

	public void setSonEntity(SonEntity sonEntity) {
		this.sonEntity = sonEntity;
	}

	@Override
	public String toString() {
		return "SocialMediaEntity [id=" + id + ", accessToken=" + accessToken + ", type=" + type + ", invalidToken="
				+ invalidToken + ", sonEntity=" + sonEntity + "]";
	}
}
