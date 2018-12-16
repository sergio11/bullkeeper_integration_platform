package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SmsFolderNameType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SmsReadStateType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.BirthdayDeserializer;

/**
 * Save SMS DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveSmsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Address
	 */
	@JsonProperty("address")
	@NotBlank(message = "{sms.address.notnull}")
	private String address;
	
	/**
	 * Message
	 */
	@JsonProperty("message")
	@NotBlank(message = "{sms.message.notnull}")
	private String message;
	
	/**
	 * Read State
	 */
	@JsonProperty("read_state")
	@SmsReadStateType(message="{sms.readstate.invalid}")
	private String readState;
	
	/**
	 * Date
	 */
	@JsonProperty("date")
	@JsonDeserialize(using = BirthdayDeserializer.class)
    private Date date;
	
	/**
	 * Folder Name
	 */
	@JsonProperty("folder_name")
	@SmsFolderNameType(message="{sms.foldername.invalid}")
	private String folderName;
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	@NotBlank(message = "{sms.localid.notnull}")
	private String localId;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	@TerminalShouldExists(message="{terminal.not.exists}")
	private String terminal;
	
	/**
	 * KId
	 */
	@JsonProperty("kid")
	@KidShouldExists(message="{kid.not.exists}")
	private String kid;
	
	
	public SaveSmsDTO() {}

	/**
	 * 
	 * @param address
	 * @param message
	 * @param readState
	 * @param date
	 * @param folderName
	 * @param localId
	 * @param terminal
	 * @param kid
	 */
	public SaveSmsDTO(String address, String message, String readState, Date date,
			String folderName, String localId, String terminal,
			String kid) {
		super();
		this.address = address;
		this.message = message;
		this.readState = readState;
		this.date = date;
		this.folderName = folderName;
		this.localId = localId;
		this.terminal = terminal;
		this.kid = kid;
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

	public Date getDate() {
		return date;
	}

	public String getFolderName() {
		return folderName;
	}

	public String getLocalId() {
		return localId;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getKid() {
		return kid;
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

	public void setDate(Date date) {
		this.date = date;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "SaveSmsDTO [address=" + address + ", message=" + message + ", readState=" + readState + ", date=" + date
				+ ", folderName=" + folderName + ", localId=" + localId + ", terminal=" + terminal + ", kid=" + kid
				+ "]";
	}
}
