package sanchez.sanchez.sergio.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */
@Document(collection="comments")
public class CommentEntity {
    
    @Id
    private ObjectId id;
    
    @Field("message")
    private String message;
    
    private ObjectId user;

    @PersistenceConstructor
    public CommentEntity(String message, ObjectId user) {
        this.message = message;
        this.user = user;
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

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

}
