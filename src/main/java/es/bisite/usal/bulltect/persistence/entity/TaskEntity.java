package es.bisite.usal.bulltect.persistence.entity;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import es.bisite.usal.bulltect.persistence.utils.CascadeSave;

/**
 * @author sergio
 */
@Document(collection = TaskEntity.COLLECTION_NAME)
public class TaskEntity {
    
    public final static String COLLECTION_NAME = "tasks";
    
    @Id
    private ObjectId id;
    
    @Field("start_date")
    private Date startDate;
    
    @Field("finish_date")
    private Date finishDate;
    
    @Field("duration")
    private Long duration;
    
    @Field("is_success")
    private Boolean success = Boolean.TRUE;
    
    @Field("comments")
    @DBRef
    @CascadeSave
    private List<CommentEntity> comments;
    
    @Field("target")
    @DBRef
    private SonEntity sonEntity;
    
    @Field("social_media_id")
    private ObjectId socialMediaId;
    
    public TaskEntity(){}
    
    @PersistenceConstructor
    public TaskEntity(Date startDate, Date finishDate, Long duration, Boolean success, List<CommentEntity> comments, SonEntity sonEntity, ObjectId socialMediaId) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.success = success;
        this.comments = comments;
        this.sonEntity = sonEntity;
        this.socialMediaId = socialMediaId;
    }

    public ObjectId getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
   

    public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

	public SonEntity getSonEntity() {
		return sonEntity;
	}

	public void setSonEntity(SonEntity sonEntity) {
		this.sonEntity = sonEntity;
	}

	public ObjectId getSocialMediaId() {
		return socialMediaId;
	}

	public void setSocialMediaId(ObjectId socialMediaId) {
		this.socialMediaId = socialMediaId;
	}

}
