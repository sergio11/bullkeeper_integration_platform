package es.bisite.usal.bulltect.persistence.entity;

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

    @Field("social_media")
    private SocialMediaTypeEnum socialMedia;

    @Field("status")
    private CommentStatusEnum status = CommentStatusEnum.PENDING;

    @Field("from")
    private String from;
    
    @Field("from_id")
    private String fromId;

    @Field("target")
    @DBRef
    private SonEntity sonEntity;

    public CommentEntity() {
    }

    @PersistenceConstructor
    public CommentEntity(String message, Long likes, Date createdTime,
            SocialMediaTypeEnum socialMedia, CommentStatusEnum status, String from, String fromId, SonEntity sonEntity) {
        super();
        this.message = message;
        this.likes = likes;
        this.createdTime = createdTime;
        this.socialMedia = socialMedia;
        this.status = status;
        this.from = from;
        this.fromId = fromId;
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

    public SocialMediaTypeEnum getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMediaTypeEnum socialMedia) {
        this.socialMedia = socialMedia;
    }

    public CommentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CommentStatusEnum status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	@Override
	public String toString() {
		return "CommentEntity [id=" + id + ", message=" + message + ", likes=" + likes + ", createdTime=" + createdTime
				+ ", socialMedia=" + socialMedia + ", status=" + status + ", from=" + from + ", fromId=" + fromId
				+ ", sonEntity=" + sonEntity + "]";
	}
}
