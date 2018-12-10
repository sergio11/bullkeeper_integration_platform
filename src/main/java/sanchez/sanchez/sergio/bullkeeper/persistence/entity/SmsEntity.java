package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * SMS Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = SmsEntity.COLLECTION_NAME)
public final class SmsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "sms";

	/**
	 * Id
	 */
    @Id
    private ObjectId id;
    
    /**
     * Address
     */
    @Field("address")
    private String address;
    
    /**
     * Message
     */
    @Field("message")
    private String message;
    
    /**
     * Read State
     */
    @Field("read_state")
    private SmsReadStateEnum readState;
    
    /**
     * Date
     */
    @Field("date")
    private Date date;
    
    /**
     * Folder Name
     */
    @Field("folder_name")
    private SmsFolderNameEnum folderName;
    
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

	
    public SmsEntity() {}
    
    /**
     * 
     * @param id
     * @param address
     * @param message
     * @param readState
     * @param date
     * @param folderName
     * @param terminal
     * @param kid
     */
	public SmsEntity(ObjectId id, String address, String message, SmsReadStateEnum readState, Date date,
			SmsFolderNameEnum folderName, TerminalEntity terminal, KidEntity kid) {
		super();
		this.id = id;
		this.address = address;
		this.message = message;
		this.readState = readState;
		this.date = date;
		this.folderName = folderName;
		this.terminal = terminal;
		this.kid = kid;
	}

	public ObjectId getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public String getMessage() {
		return message;
	}

	public SmsReadStateEnum getReadState() {
		return readState;
	}

	public Date getDate() {
		return date;
	}

	public SmsFolderNameEnum getFolderName() {
		return folderName;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setReadState(SmsReadStateEnum readState) {
		this.readState = readState;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setFolderName(SmsFolderNameEnum folderName) {
		this.folderName = folderName;
	}



	public TerminalEntity getTerminal() {
		return terminal;
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
		return "SmsEntity [id=" + id + ", address=" + address + ", message=" + message + ", readState=" + readState
				+ ", date=" + date + ", folderName=" + folderName + ", terminal=" + terminal + ", kid="
				+ kid + "]";
	}

	
	
}
