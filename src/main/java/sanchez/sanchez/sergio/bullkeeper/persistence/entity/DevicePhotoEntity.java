package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Device Photo Entity
 * @author ssanchez
 *
 */
@Document(collection = UserSystemEntity.COLLECTION_NAME)
public final class DevicePhotoEntity {
	
	
	public final static String COLLECTION_NAME = "device_photos";
	
	
	/**
	 * Id
	 */
    @Id
    private ObjectId id;
    
    /**
     * Name
     */
    @Field("name")
    private String name;
	
	/**
	 * Image Id (strategy resource id)
	 */
	@Field("image_id")
	private String imageId;
	
	/**
	 * Terminal
	 */
	@Field("terminal")
	private TerminalEntity terminal;
	
	/**
	 * Kid
	 */
	@Field("kid")
	private KidEntity kid;
	
	
	public DevicePhotoEntity() {}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param imageId
	 * @param terminal
	 * @param kid
	 */
	@PersistenceConstructor
	public DevicePhotoEntity(ObjectId id, String name, String imageId, TerminalEntity terminal, KidEntity kid) {
		super();
		this.id = id;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "DevicePhotoEntity [id=" + id + ", name=" + name + ", imageId=" + imageId + ", terminal=" + terminal
				+ ", kid=" + kid + "]";
	}
	

}
