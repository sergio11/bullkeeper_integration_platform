package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Conversarion Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = ConversationEntity.COLLECTION_NAME)
public class ConversationEntity {
	
	public final static String COLLECTION_NAME = "conversations";
	
	/**
     * Id
     */
    @Id
    private ObjectId id;
	
	/**
     * Create At
     */
    @Field("create_at")
    private Date createAt = new Date();
    
    
    /**
     * Update At
     */
    @Field("update_at")
    private Date updateAt = new Date();
    
    /**
     * Supervised Children
     */
    @Field("supervised_children")
    @DBRef
    private SupervisedChildrenEntity supervisedChildrenEntity;

    
    
    public ConversationEntity() {}
    
    /**
     * 
     * @param createAt
     * @param updateAt
     * @param supervisedChildrenEntity
     */
    @PersistenceConstructor
	public ConversationEntity(Date createAt, Date updateAt, SupervisedChildrenEntity supervisedChildrenEntity) {
		super();
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.supervisedChildrenEntity = supervisedChildrenEntity;
	}

	public ObjectId getId() {
		return id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public SupervisedChildrenEntity getSupervisedChildrenEntity() {
		return supervisedChildrenEntity;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public void setSupervisedChildrenEntity(SupervisedChildrenEntity supervisedChildrenEntity) {
		this.supervisedChildrenEntity = supervisedChildrenEntity;
	}

}
