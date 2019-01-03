package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
	 * Latitude
	 */
	@Field("latitude")
	private double lat;
	
	/**
	 * Longitude
	 */
	@Field("longitude")
	private double log;

	
	/**
	 * Radius
	 */
	@Field("radius")
	private float radius;
	
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
	
	
	public GeofenceEntity() {}
	
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param lat
	 * @param log
	 * @param radius
	 * @param type
	 * @param kid
	 */
	@PersistenceConstructor
	public GeofenceEntity(ObjectId id, String name, double lat, double log, float radius,
			GeofenceTransitionTypeEnum type, KidEntity kid) {
		super();
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.log = log;
		this.radius = radius;
		this.type = type;
		this.kid = kid;
	}

	public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
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

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLog(double log) {
		this.log = log;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void setType(GeofenceTransitionTypeEnum type) {
		this.type = type;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}


	@Override
	public String toString() {
		return "GeofenceEntity [id=" + id + ", name=" + name + ", lat=" + lat + ", log=" + log + ", radius=" + radius
				+ ", type=" + type + ", kid=" + kid + "]";
	}
}
