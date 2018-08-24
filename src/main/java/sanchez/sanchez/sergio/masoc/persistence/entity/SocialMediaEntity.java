package sanchez.sanchez.sergio.masoc.persistence.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
    
    @Field("refresh_token")
    private String refreshToken;
    
    @Field("social_media_type")
    private SocialMediaTypeEnum type;
    
    @Field("user_social_name")
    private String userSocialName;
    
    @Field("user_picture")
    private String userPicture;
    
    @Field("invalid_token")
    private Boolean invalidToken = Boolean.FALSE;
    
    @Field("scheduled_for")
    private Long scheduledFor;
    
    @Field("last_probing")
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private Date lastProbing;
    
    @Field("target")
    @DBRef
    private SonEntity sonEntity;
    
    public SocialMediaEntity(){}
    
    public SocialMediaEntity(String accessToken, SocialMediaTypeEnum type,  SonEntity sonEntity) {
		super();
		this.accessToken = accessToken;
		this.type = type;
		this.sonEntity = sonEntity;
		this.scheduledFor = new Date().getTime();
	}
    
    public SocialMediaEntity(String accessToken, SocialMediaTypeEnum type, String userSocialName,  SonEntity sonEntity) {
		super();
		this.accessToken = accessToken;
		this.type = type;
		this.userSocialName = userSocialName;
		this.sonEntity = sonEntity;
		this.scheduledFor = new Date().getTime();
	}

    @PersistenceConstructor
    public SocialMediaEntity(String accessToken, String refreshToken, SocialMediaTypeEnum type, String userSocialName, String userPicture, Boolean invalidToken, Long scheduledFor,
			Date lastProbing, SonEntity sonEntity) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.type = type;
		this.userSocialName = userSocialName;
		this.userPicture = userPicture;
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
    

    public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Boolean getInvalidToken() {
		return invalidToken;
	}

	public SocialMediaTypeEnum getType() {
        return type;
    }

    public void setType(SocialMediaTypeEnum type) {
        this.type = type;
    }
    
	public String getUserSocialName() {
		return userSocialName;
	}

	public void setUserSocialName(String userSocialName) {
		this.userSocialName = userSocialName;
	}
	
	public String getUserPicture() {
		return userPicture;
	}

	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}

	public Boolean isInvalidToken() {
        return invalidToken;
    }

    public void setInvalidToken(Boolean invalidToken) {
        this.invalidToken = invalidToken;
    }
   

	public Long getScheduledFor() {
		return scheduledFor;
	}

	public void setScheduledFor(Long scheduledFor) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocialMediaEntity other = (SocialMediaEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SocialMediaEntity [id=" + id + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken
				+ ", type=" + type + ", userSocialName=" + userSocialName + ", userPicture=" + userPicture
				+ ", invalidToken=" + invalidToken + ", scheduledFor=" + scheduledFor + ", lastProbing=" + lastProbing
				+ ", sonEntity=" + sonEntity + "]";
	}

	
}
