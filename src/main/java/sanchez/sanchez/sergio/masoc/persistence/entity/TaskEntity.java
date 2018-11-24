package sanchez.sanchez.sergio.masoc.persistence.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sanchez.sanchez.sergio.masoc.persistence.utils.CascadeSave;

/**
 * @author sergio
 */
@Document(collection = TaskEntity.COLLECTION_NAME)
public class TaskEntity {
    
    public final static String COLLECTION_NAME = "tasks";
    
    /**
     * Id
     */
    @Id
    private ObjectId id;
    
    /**
     * Start Date
     */
    @Field("start_date")
    private Date startDate;
    
    /**
     * Finish Date
     */
    @Field("finish_date")
    private Date finishDate;
    
    /**
     * Duration
     */
    @Field("duration")
    private Long duration;
    
    /**
     * Is Success
     */
    @Field("is_success")
    private Boolean success = Boolean.TRUE;
    
    /**
     * Comments
     */
    @Field("comments")
    @DBRef
    @CascadeSave
    private Set<CommentEntity> comments = new HashSet<>();
    
    /**
     * Target
     */
    @Field("target")
    @DBRef
    private SonEntity sonEntity;
    
    /**
     * Social Media Id
     */
    @Field("social_media_id")
    private ObjectId socialMediaId;
    
    
    public TaskEntity(){}
    
    @PersistenceConstructor
    public TaskEntity(Date startDate, Date finishDate, Long duration, Boolean success, Set<CommentEntity> comments, SonEntity sonEntity, ObjectId socialMediaId) {
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

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
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
