package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Phone Number Blocked
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = PhoneNumberBlockedEntity.COLLECTION_NAME)
public class PhoneNumberBlockedEntity {
	
	public final static String COLLECTION_NAME = "phonenumber_blocked";
	
	/**
     * Id
     */
    @Id
    private ObjectId id;
    
    /**
     * Blocked At
     */
    @Field("blocked_at")
    private Date blockedAt = new Date();

    
    /**
     * Phone Number
     */
    @Field("phone_number")
    private String phoneNumber;
    
    /**
     * Terminal
     */
    @Field("terminal")
    @DBRef
    private TerminalEntity terminal;
    
    /**
     * Kid
     */
    @Field("kid")
    @DBRef
    private KidEntity kid;

    
    public PhoneNumberBlockedEntity() {}
    
    /**
     * 
     * @param id
     * @param blockedAt
     * @param phoneNumber
     * @param terminal
     * @param kid
     */
    @PersistenceConstructor
	public PhoneNumberBlockedEntity(final ObjectId id, final Date blockedAt,
			final String phoneNumber, final TerminalEntity terminal, final KidEntity kid) {
		super();
		this.id = id;
		this.blockedAt = blockedAt;
		this.phoneNumber = phoneNumber;
		this.terminal = terminal;
		this.kid = kid;
	}

	public ObjectId getId() {
		return id;
	}

	public Date getBlockedAt() {
		return blockedAt;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setBlockedAt(Date blockedAt) {
		this.blockedAt = blockedAt;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}
	

	public KidEntity getKid() {
		return kid;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "PhoneNumberBlockedEntity [id=" + id + ", blockedAt=" + blockedAt + ", phoneNumber=" + phoneNumber
				+ ", terminal=" + terminal + ", kid=" + kid + "]";
	}

	
}
