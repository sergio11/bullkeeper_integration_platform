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
@Document(collection = CommentEntity.COLLECTION_NAME)
public class CommentEntity {
    
    public final static String COLLECTION_NAME = "comments";
    
    @Id
    private ObjectId id;
    
    @Field("message")
    private String message;
    
    @Field("likes")
    private Long likes = 0l;
    
    @Field("created_time")
    private Date createdTime;
    
    @DBRef
    private SonEntity sonEntity;
    
    public CommentEntity(){}

    @PersistenceConstructor
    public CommentEntity(String message, Date createdTime, SonEntity sonEntity) {
        this.message = message;
        this.createdTime = createdTime;
        this.sonEntity = sonEntity;
    }
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public SonEntity getSonEntity() {
		return sonEntity;
	}

	public void setSonEntity(SonEntity sonEntity) {
		this.sonEntity = sonEntity;
	}

	public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    

	@Override
    public String toString() {
        return "CommentEntity{" + "id=" + id + ", message=" + message + ", likes=" + likes + ", createdTime=" + createdTime + ", userEntity=" + sonEntity + '}';
    }
}
