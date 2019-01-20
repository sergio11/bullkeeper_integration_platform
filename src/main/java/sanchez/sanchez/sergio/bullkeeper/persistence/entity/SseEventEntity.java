package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * SSE Event Type
 * @author sergiosanchezsanchez
 */
@Document(collection = SseEventEntity.COLLECTION_NAME)
public class SseEventEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "sse_event";
	
	/**
	 * Id
	 */
	@Id
	private ObjectId id;
	
	/**
	 * Target
	 */
	@Field("target")
	private String target;
	
	/**
	 * Message
	 */
	@Field("message")
	private String message;
	
	
	/**
	 * 
	 * @param target
	 * @param message
	 */
	public SseEventEntity(final String target, final String message) {
		super();
		this.target = target;
		this.message = message;
	}

	/**
	 * 
	 * @param id
	 * @param target
	 * @param message
	 */
	@PersistenceConstructor
	public SseEventEntity(final ObjectId id, final String target, 
			final String message) {
		super();
		this.id = id;
		this.target = target;
		this.message = message;
	}

	public ObjectId getId() {
		return id;
	}

	public String getTarget() {
		return target;
	}

	public String getMessage() {
		return message;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SseEventEntity [id=" + id + ", target=" + target + ", message=" + message + "]";
	}
}
