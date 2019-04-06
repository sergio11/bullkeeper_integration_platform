package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Device Photo Entity
 * @author ssanchez
 *
 */
@Document(collection = DevicePhotoEntity.COLLECTION_NAME)
public final class DevicePhotoEntity {
	
	
	public final static String COLLECTION_NAME = "device_photos";
	
	
	/**
	 * Id
	 */
    @Id
    private ObjectId id;
    
    /**
	 * Display Name
	 */
    @Field("display_name")
	private String displayName;
	
	/**
	 * Path
	 */
	@Field("path")
	private String path;
	
	/**
	 * Date Added
	 */
	@Field("date_added")
	private Long dateAdded; 
	
	/**
	 * Date Modified
	 */
	@Field("date_modified")
	private Long dateModified;
	
	/**
	 * Date Taken
	 */
	@Field("date_taken")
	private Long dateTaken;
	
	/**
	 * Height
	 */
	@Field("height")
	private Integer height;
	
	/**
	 * Width
	 */
	@Field("width")
	private Integer width;
	
	/**
	 * Orientation
	 */
	@Field("orientation")
	private Integer orientation;
	
	/**
	 * Size
	 */
	@Field("size")
	private Integer size;
	
	/**
	 * Local Id
	 */
	@Field("local_id")
	private String localId;
	
	/**
	 * Disabled
	 */
	@Field("disabled")
	private Boolean disabled = false;
	
	/**
	 * Image Id (strategy resource id)
	 */
	@Field("image_id")
	private String imageId;
	
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
	
	
	public DevicePhotoEntity() {}

	/**
	 * 
	 * @param id
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
	 * @param disabled
	 * @param imageId
	 * @param terminal
	 * @param kid
	 */
	@PersistenceConstructor
	public DevicePhotoEntity(ObjectId id, String displayName, String path, Long dateAdded, Long dateModified,
			Long dateTaken, Integer height, Integer width, Integer orientation, Integer size, String localId,
			Boolean disabled, String imageId, TerminalEntity terminal, KidEntity kid) {
		super();
		this.id = id;
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
		this.disabled = disabled;
		this.imageId = imageId;
		this.terminal = terminal;
		this.kid = kid;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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
		return "DevicePhotoEntity [id=" + id + ", displayName=" + displayName + ", path=" + path + ", dateAdded="
				+ dateAdded + ", dateModified=" + dateModified + ", dateTaken=" + dateTaken + ", height=" + height
				+ ", width=" + width + ", orientation=" + orientation + ", size=" + size + ", localId=" + localId
				+ ", disabled=" + disabled + ", imageId=" + imageId + ", terminal=" + terminal + ", kid=" + kid + "]";
	}
}
