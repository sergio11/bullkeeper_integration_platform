package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Contact Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = ContactEntity.COLLECTION_NAME)
public class ContactEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "contacts";

	/**
	 * App id
	 */
	@Id
	private ObjectId id;
	
	/**
	 * Name
	 */
	@Field("name")
	private String name;
	
	
	/**
	 * Phone Number
	 */
	@Field("phone_number")
	private String phoneNumber;
	
	/**
	 * Photo Encoded String
	 */
	@Field("photo_encoded_string")
	private String photoEncodedString;
	
	/**
	 * Local Id
	 */
	@Field("local_id")
	protected String localId;
	
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
	
	public ContactEntity() {}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param phoneNumber
	 * @param kid
	 * @param terminal
	 */
	@PersistenceConstructor
	public ContactEntity(final ObjectId id, final String name, final String phoneNumber,
			final String photoEncodedString, final String localId, 
			final KidEntity kid, final TerminalEntity terminal) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.photoEncodedString = photoEncodedString;
		this.localId = localId;
		this.kid = kid;
		this.terminal = terminal;
	}

	

	public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPhotoEncodedString() {
		return photoEncodedString;
	}

	public String getLocalId() {
		return localId;
	}

	public KidEntity getKid() {
		return kid;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhotoEncodedString(String photoEncodedString) {
		this.photoEncodedString = photoEncodedString;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "ContactEntity [id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", localId=" + localId
				+ ", kid=" + kid + ", terminal=" + terminal + "]";
	}
	
}
