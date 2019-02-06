package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

/**
 * Geofences Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = GeofenceEntity.COLLECTION_NAME)
public final class GeofenceEntity {
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "geofences";
	
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
     * address
     */
    @Field("address")
    private String address;
    
    
    /**
	 * Latitude
	 */
	@Field("latitude")
	private Double lat;
	
	/**
	 * Longitude
	 */
	@Field("longitude")
	private Double log;

	
	/**
	 * Radius
	 */
	@Field("radius")
	private Float radius;
	
	/**
	 * Type
	 */
	@Field("transition_type")
	private GeofenceTransitionTypeEnum type;
	
	/**
	 * Kid
	 */
	@Field("kid")
	@DBRef
	private KidEntity kid;
	
	/**
	 * Create At
	 */
	@Field("create_at")
	private Date createAt = new Date();
	
	/**
	 * Update At
	 */
	@Field("update_at")
	private Date updateAt = new Date();
	
	/**
	 * Is Enabled
	 */
	@Field("is_enabled")
	private Boolean isEnabled = Boolean.TRUE;
	
	
	/**
	 * Alerts
	 */
	@Field("alerts")
    @CascadeSave
	private List<GeofenceAlertEntity> alerts = new ArrayList<GeofenceAlertEntity>();
	
	
	public GeofenceEntity() {}
	

	/**
	 * 
	 * @param id
	 * @param name
	 * @param address
	 * @param lat
	 * @param log
	 * @param radius
	 * @param type
	 * @param kid
	 * @param createAt
	 * @param updateAt
	 * @param isEnabled
	 * @param alerts
	 * 
	 */
	@PersistenceConstructor
	public GeofenceEntity(final ObjectId id, final String name, final String address,
			Double lat, Double log, Float radius, final GeofenceTransitionTypeEnum type, 
			final KidEntity kid, final Date createAt, final Date updateAt, 
			final Boolean isEnabled, final List<GeofenceAlertEntity> alerts) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.lat = lat;
		this.log = log;
		this.radius = radius;
		this.type = type;
		this.kid = kid;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.isEnabled = isEnabled;
		this.alerts = alerts;
	}

	public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public double getLat() {
		return lat;
	}

	public double getLog() {
		return log;
	}

	public float getRadius() {
		return radius;
	}

	public GeofenceTransitionTypeEnum getType() {
		return type;
	}

	public KidEntity getKid() {
		return kid;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLog(Double log) {
		this.log = log;
	}

	public void setRadius(Float radius) {
		this.radius = radius;
	}

	public void setType(GeofenceTransitionTypeEnum type) {
		this.type = type;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}


	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}


	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	

	public List<GeofenceAlertEntity> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<GeofenceAlertEntity> alerts) {
		this.alerts = alerts;
	}


	@Override
	public String toString() {
		return "GeofenceEntity [id=" + id + ", name=" + name + ", address=" + address + ", lat=" + lat + ", log=" + log
				+ ", radius=" + radius + ", type=" + type + ", kid=" + kid + ", createAt=" + createAt + ", updateAt="
				+ updateAt + ", isEnabled=" + isEnabled + "]";
	}

}
