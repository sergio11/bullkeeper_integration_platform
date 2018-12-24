package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * SMS DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SmsDTO implements Serializable {

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
	 * Address
	 */
	@JsonProperty("address")
	private String address;
	
	
	/**
	 * Message
	 */
	@JsonProperty("message")
	private String message;
	
	
	/**
	 * Read State
	 */
	@JsonProperty("read_state")
	private String readState;
	
	
	/**
	 * Date
	 */
	@JsonProperty("date")
	private String date;
	
	
	/**
	 * Folder Name
	 */
	@JsonProperty("folder_name")
	private String folderName;
	
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	protected String localId;

	
	public SmsDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param address
	 * @param message
	 * @param readState
	 * @param date
	 * @param folderName
	 * @param terminal
	 * @param kid
	 * @param localId
	 */
	public SmsDTO(String identity, String address, String message, String readState, String date, String folderName,
			String terminal, String kid,
			String localId) {
		super();
		this.identity = identity;
		this.address = address;
		this.message = message;
		this.readState = readState;
		this.date = date;
		this.folderName = folderName;
		this.terminal = terminal;
		this.kid = kid;
		this.localId = localId;
	}


	public String getIdentity() {
		return identity;
	}


	public String getAddress() {
		return address;
	}


	public String getMessage() {
		return message;
	}


	public String getReadState() {
		return readState;
	}


	public String getDate() {
		return date;
	}


	public String getFolderName() {
		return folderName;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public void setReadState(String readState) {
		this.readState = readState;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	@Override
	public String toString() {
		return "SmsDTO [identity=" + identity + ", address=" + address + ", message=" + message + ", readState="
				+ readState + ", date=" + date + ", folderName=" + folderName + ", terminal=" + terminal + ", kid="
				+ kid + ", localId=" + localId + "]";
	}

	
}
