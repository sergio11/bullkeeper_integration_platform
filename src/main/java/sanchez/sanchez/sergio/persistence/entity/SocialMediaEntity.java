package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;

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
    
    @Field("scheduled_for")
    private Date scheduledFor;
    
    @Field("last_probing")
    private Date lastProbing;
    
    @Field("target")
    @DBRef
    private SonEntity sonEntity;
    
    public SocialMediaEntity(){}
    
    public SocialMediaEntity(String accessToken, SocialMediaTypeEnum type, SonEntity sonEntity) {
		super();
		this.accessToken = accessToken;
		this.type = type;
		this.sonEntity = sonEntity;
	}

    @PersistenceConstructor
    public SocialMediaEntity(String accessToken, SocialMediaTypeEnum type, Boolean invalidToken, Date scheduledFor,
			Date lastProbing, SonEntity sonEntity) {
		super();
		this.accessToken = accessToken;
		this.type = type;
		this.invalidToken = invalidToken;
		this.scheduledFor = scheduledFor;
		this.lastProbing = lastProbing;
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
    

	public Date getScheduledFor() {
		return scheduledFor;
	}

	public void setScheduledFor(Date scheduledFor) {
		this.scheduledFor = scheduledFor;
	}

	public Date getLastProbing() {
		return lastProbing;
	}

	public void setLastProbing(Date lastProbing) {
		this.lastProbing = lastProbing;
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
