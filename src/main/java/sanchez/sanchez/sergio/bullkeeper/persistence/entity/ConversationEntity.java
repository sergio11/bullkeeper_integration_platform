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
     * Member One
     */
    @DBRef
    @Field("member_one")
    private PersonEntity memberOne;
    
    
    /**
     * Member Two
     */
    @DBRef
    @Field("member_two")
    private PersonEntity memberTwo;
    

    public ConversationEntity() {}
    
    /**
     * 
     * @param createAt
     * @param updateAt
     * @param memberOne
     * @param memberTwo
     */
    @PersistenceConstructor
	public ConversationEntity(final Date createAt, final Date updateAt, 
			final PersonEntity memberOne, final PersonEntity memberTwo) {
		super();
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.memberOne = memberOne;
		this.memberTwo = memberTwo;
	}

    /**
     * @param memberOne
     * @param memberTwo
     */
    public ConversationEntity(final PersonEntity memberOne, final PersonEntity memberTwo) {
    	this.memberOne = memberOne;
    	this.memberTwo = memberTwo;
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

	public PersonEntity getMemberOne() {
		return memberOne;
	}

	public PersonEntity getMemberTwo() {
		return memberTwo;
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

	public void setMemberOne(PersonEntity memberOne) {
		this.memberOne = memberOne;
	}

	public void setMemberTwo(PersonEntity memberTwo) {
		this.memberTwo = memberTwo;
	}

	@Override
	public String toString() {
		return "ConversationEntity [id=" + id + ", createAt=" + createAt + ", updateAt=" + updateAt + ", memberOne="
				+ memberOne + ", memberTwo=" + memberTwo + "]";
	}
}
