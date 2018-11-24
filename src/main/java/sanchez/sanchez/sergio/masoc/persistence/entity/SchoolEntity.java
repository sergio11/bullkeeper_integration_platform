package sanchez.sanchez.sergio.masoc.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * School Enity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = SchoolEntity.COLLECTION_NAME)
public class SchoolEntity {
	
	public final static String COLLECTION_NAME = "schools";
	
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
	 * Residence
	 */
	@Field("residence")
	private String residence;
	
	/**
	 * School Location
	 */
	@Field("location")
	private SchoolLocation location = new SchoolLocation();
	
	/**
	 * Province
	 */
	@Field("province")
	private String province;
	
	/**
	 * Tfno
	 */
	@Field("tfno")
	private Integer tfno;
	
	/**
	 * Email
	 */
	@Field("email")
	private String email;

	
	public SchoolEntity(){}
	
	@PersistenceConstructor
	public SchoolEntity(String name, String residence, SchoolLocation location, String province, Integer tfno, String email) {
		super();
		this.name = name;
		this.residence = residence;
		this.location = location;
		this.province = province;
		this.tfno = tfno;
		this.email = email;
	}
	
	public SchoolEntity(String name, String residence, String province, Integer tfno, String email) {
		super();
		this.name = name;
		this.residence = residence;
		this.province = province;
		this.tfno = tfno;
		this.email = email;
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

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public SchoolLocation getLocation() {
		return location;
	}

	public void setLocation(SchoolLocation location) {
		this.location = location;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getTfno() {
		return tfno;
	}

	public void setTfno(Integer tfno) {
		this.tfno = tfno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public static class SchoolLocation {
		
		@Field("latitude")
		private Double latitude;
		
		@Field("longitude")
		private Double longitude;
		
		public SchoolLocation(){}
		

		@PersistenceConstructor
		public SchoolLocation(Double latitude, Double longitude) {
			super();
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public Double getLatitude() {
			return latitude;
		}

		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

		public Double getLongitude() {
			return longitude;
		}

		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}
		
	}

	@Override
	public String toString() {
		return "SchoolEntity [id=" + id + ", name=" + name + ", residence=" + residence + ", location=" + location
				+ ", province=" + province + ", tfno=" + tfno + ", email=" + email + "]";
	}

}
