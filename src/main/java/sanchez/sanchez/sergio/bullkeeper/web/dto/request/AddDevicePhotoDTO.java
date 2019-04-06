package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;

/**
 * 
 * @author ssanchez
 *
 */
public final class AddDevicePhotoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Kid ID
	 */
	@ValidObjectId(message = "{id.not.valid}")
    @KidShouldExists(message = "{kid.should.be.exists}", groups = Extended.class)
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal Id
	 */
	@ValidObjectId(message = "{id.not.valid}")
    @TerminalShouldExists(message = "{terminal.not.exists}", groups = Extended.class)
	@JsonProperty("terminal")
	private String terminal;
	
	/**
	 * Display Name
	 */
	@JsonProperty("display_name")
	private String displayName;
	
	/**
	 * Path
	 */
	@JsonProperty("path")
	private String path;
	
	/**
	 * Date Added
	 */
	@JsonProperty("date_added")
	private Long dateAdded; 
	
	/**
	 * Date Modified
	 */
	@JsonProperty("date_modified")
	private Long dateModified;
	
	/**
	 * Date Taken
	 */
	@JsonProperty("date_taken")
	private Long dateTaken;
	
	/**
	 * Height
	 */
	@JsonProperty("height")
	private Integer height;
	
	/**
	 * Width
	 */
	@JsonProperty("width")
	private Integer width;
	
	/**
	 * Orientation
	 */
	@JsonProperty("orientation")
	private Integer orientation;
	
	/**
	 * Size
	 */
	@JsonProperty("size")
	private Integer size;
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	private String localId;
	
	public AddDevicePhotoDTO() {}

	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param displayName
	 * @param path
	 * @param dateAdded
	 * @param dateModified
	 * @param dateTaken
	 * @param height
	 * @param width
	 * @param orientation
	 * @param size
	 * @param localId
	 */
	public AddDevicePhotoDTO(
			String kid, String terminal, String displayName, String path, Long dateAdded, Long dateModified, Long dateTaken, Integer height,
			Integer width, Integer orientation, Integer size, String localId) {
		super();
		this.kid = kid;
		this.terminal = terminal;
		this.displayName = displayName;
		this.path = path;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.dateTaken = dateTaken;
		this.height = height;
		this.width = width;
		this.orientation = orientation;
		this.size = size;
		this.localId = localId;
	}


	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Long dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Long getDateModified() {
		return dateModified;
	}

	public void setDateModified(Long dateModified) {
		this.dateModified = dateModified;
	}

	public Long getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(Long dateTaken) {
		this.dateTaken = dateTaken;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getOrientation() {
		return orientation;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	@Override
	public String toString() {
		return "AddDevicePhotoDTO [kid=" + kid + ", terminal=" + terminal + ", displayName=" + displayName + ", path="
				+ path + ", dateAdded=" + dateAdded + ", dateModified=" + dateModified + ", dateTaken=" + dateTaken
				+ ", height=" + height + ", width=" + width + ", orientation=" + orientation + ", size=" + size
				+ ", localId=" + localId + "]";
	}

	
	
}
