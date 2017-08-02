package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sanchez.sanchez.sergio.persistence.utils.CascadeSave;

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
    
    @Field("is_success")
    private Boolean success = Boolean.TRUE;
    
    @Field("comments")
    @CascadeSave
    private List<CommentEntity> comments;
    
    @DBRef
    private UserEntity userEntity;
    
    public TaskEntity(){}
    
    @PersistenceConstructor
    public TaskEntity(Date startDate, Date finishDate, Boolean success, List<CommentEntity> comments, UserEntity userEntity) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.success = success;
        this.comments = comments;
        this.userEntity = userEntity;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}