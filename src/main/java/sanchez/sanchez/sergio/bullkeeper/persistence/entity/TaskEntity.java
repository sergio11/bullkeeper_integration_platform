package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

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
    private KidEntity kid;
    
    /**
     * Social Media Id
     */
    @Field("social_media_id")
    private ObjectId socialMediaId;
    
    
    public TaskEntity(){}
    
    @PersistenceConstructor
    public TaskEntity(Date startDate, Date finishDate, Long duration, Boolean success, Set<CommentEntity> comments, KidEntity kid, ObjectId socialMediaId) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.success = success;
        this.comments = comments;
        this.kid = kid;
        this.socialMediaId = socialMediaId;
    }

	public ObjectId getId() {
		return id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public Long getDuration() {
		return duration;
	}

	public Boolean getSuccess() {
		return success;
	}

	public Set<CommentEntity> getComments() {
		return comments;
	}

	public KidEntity getKid() {
		return kid;
	}

	public ObjectId getSocialMediaId() {
		return socialMediaId;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public void setComments(Set<CommentEntity> comments) {
		this.comments = comments;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public void setSocialMediaId(ObjectId socialMediaId) {
		this.socialMediaId = socialMediaId;
	}

    

}
