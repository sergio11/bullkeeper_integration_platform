package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

/**
 * Kid Request Entity
 * @author sergiosanchezsanchez
 */
@Document(collection = KidRequestEntity.COLLECTION_NAME)
public class KidRequestEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "kid_requests";
	
	/**
	 * Id
	 */
    @Id
    private ObjectId id;

	/**
	 * Type
	 */
	@Field("type")
	private RequestTypeEnum type;
	
	/**
	 * Request At
	 */
	@Field("request_at")
	private Date requestAt = new Date();
	
	/**
	 * Expired At
	 */
	@Field("expired_at")
	private Date expiredAt;
	
	/**
     * Location
     */
    @Field("location")
    @CascadeSave
    private LocationEntity location;
    
    /**
     * Kid
     */
    @Field("kid")
    @DBRef
    private KidEntity kid;
    
    /**
     * Terminal
     */
    @Field("terminal")
    @DBRef
    private TerminalEntity terminal;
	
	/**
	 * 
	 */
	public KidRequestEntity() {}

	/**
	 * @param id
	 * @param type
	 * @param requestAt
	 * @param location
	 * @param kid
	 * @param terminal
	 */
	@PersistenceConstructor
	public KidRequestEntity(final ObjectId id, final RequestTypeEnum type, final Date requestAt,
			final LocationEntity location, final KidEntity kid, final TerminalEntity terminal) {
		super();
		this.id = id;
		this.type = type;
		this.requestAt = requestAt;
		this.location = location;
		this.kid = kid;
		this.terminal = terminal;
	}
	
	

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public RequestTypeEnum getType() {
		return type;
	}

	public Date getRequestAt() {
		return requestAt;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public LocationEntity getLocation() {
		return location;
	}

	public KidEntity getKid() {
		return kid;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setType(RequestTypeEnum type) {
		this.type = type;
	}

	public void setRequestAt(Date requestAt) {
		this.requestAt = requestAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public void setLocation(LocationEntity location) {
		this.location = location;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "KidRequestEntity [type=" + type + ", requestAt=" + requestAt + ", expiredAt=" + expiredAt
				+ ", location=" + location + ", kid=" + kid + ", terminal=" + terminal + "]";
	}

	
	
}
