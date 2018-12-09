package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Message Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = MessageEntity.COLLECTION_NAME)
public class MessageEntity {
	
	public final static String COLLECTION_NAME = "messages";
	
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
     * Text
     */
    @Field("text")
    private String text;
    
    /**
     * From
     */
    @Field("from")
    @DBRef
    private PersonEntity from;
    
    /**
     * To
     */
    @Field("to")
    @DBRef
    private PersonEntity to;
    
    /**
     * Viewed
     */
    @Field("viewed")
    private boolean viewed = false;
    
	
	/**
	 * Conversation
	 */
	@Field("conversation")
	@DBRef
	private ConversationEntity conversation;

	/**
	 * 
	 */
	public MessageEntity() {}

	/**
	 * 
	 * @param id
	 * @param createAt
	 * @param text
	 * @param from
	 * @param to
	 * @param viewed
	 * @param conversation
	 */
	@PersistenceConstructor
	public MessageEntity(ObjectId id, Date createAt, String text, PersonEntity from, PersonEntity to, boolean viewed,
			ConversationEntity conversation) {
		super();
		this.id = id;
		this.createAt = createAt;
		this.text = text;
		this.from = from;
		this.to = to;
		this.viewed = viewed;
		this.conversation = conversation;
	}

	public ObjectId getId() {
		return id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public String getText() {
		return text;
	}

	public PersonEntity getFrom() {
		return from;
	}

	public PersonEntity getTo() {
		return to;
	}

	public boolean isViewed() {
		return viewed;
	}

	public ConversationEntity getConversation() {
		return conversation;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFrom(PersonEntity from) {
		this.from = from;
	}

	public void setTo(PersonEntity to) {
		this.to = to;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	public void setConversation(ConversationEntity conversation) {
		this.conversation = conversation;
	}

	
}
