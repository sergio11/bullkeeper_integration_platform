package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contact DTO
 * @author sergiosanchezsanchez
 *
 */
public final class ContactDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Name
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * Phone Number
	 */
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	protected String localId;
	
	/**
	 * Photo Encoded String
	 */
	@JsonProperty("photo_encoded_string")
	protected String photoEncodedString;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;

	public ContactDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param name
	 * @param phoneNumber
	 * @param localId
	 * @param kid
	 * @param terminal
	 */
	public ContactDTO(String identity, String name, 
			String phoneNumber, String localId, 
			String photoEncodedString, String kid, String terminal) {
		super();
		this.identity = identity;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.localId = localId;
		this.photoEncodedString = photoEncodedString;
		this.kid = kid;
		this.terminal = terminal;
	}

	

	public String getIdentity() {
		return identity;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getLocalId() {
		return localId;
	}

	public String getPhotoEncodedString() {
		return photoEncodedString;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public void setPhotoEncodedString(String photoEncodedString) {
		this.photoEncodedString = photoEncodedString;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "ContactDTO [identity=" + identity + ", name=" + name + ", phoneNumber=" + phoneNumber + ", localId="
				+ localId + ", kid=" + kid + ", terminal=" + terminal + "]";
	}
	
	

}
