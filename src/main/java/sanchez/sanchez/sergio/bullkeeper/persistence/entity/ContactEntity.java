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
	 * Disabled
	 */
	@Field("disabled")
	private Boolean disabled = false;
	
	
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
	 * @param photoEncodedString
	 * @param disabled
	 * @param localId
	 * @param kid
	 * @param terminal
	 */
	@PersistenceConstructor
	public ContactEntity(final ObjectId id, final String name, final String phoneNumber,
			final String photoEncodedString, final Boolean disabled, final String localId, 
			final KidEntity kid, final TerminalEntity terminal) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.photoEncodedString = photoEncodedString;
		this.disabled = disabled;
		this.localId = localId;
		this.kid = kid;
		this.terminal = terminal;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhotoEncodedString() {
		return photoEncodedString;
	}

	public void setPhotoEncodedString(String photoEncodedString) {
		this.photoEncodedString = photoEncodedString;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public KidEntity getKid() {
		return kid;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "ContactEntity [id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", photoEncodedString="
				+ photoEncodedString + ", disabled=" + disabled + ", localId=" + localId + ", kid=" + kid
				+ ", terminal=" + terminal + "]";
	}

	
}
