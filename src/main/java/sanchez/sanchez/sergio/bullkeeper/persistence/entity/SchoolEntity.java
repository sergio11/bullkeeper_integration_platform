package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

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
	private String tfno;
	
	/**
	 * Email
	 */
	@Field("email")
	private String email;

	
	public SchoolEntity(){}
	
	/**
	 * 
	 * @param name
	 * @param residence
	 * @param location
	 * @param province
	 * @param tfno
	 * @param email
	 */
	@PersistenceConstructor
	public SchoolEntity(final String name, final String residence, 
			final SchoolLocation location, final String province, 
			final String tfno, final String email) {
		super();
		this.name = name;
		this.residence = residence;
		this.location = location;
		this.province = province;
		this.tfno = tfno;
		this.email = email;
	}
	
	/**
	 * 
	 * @param name
	 * @param residence
	 * @param province
	 * @param tfno
	 * @param email
	 */
	public SchoolEntity(final String name, final String residence, 
			final String province, final String tfno, final String email) {
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

	public String getTfno() {
		return tfno;
	}

	public void setTfno(String tfno) {
		this.tfno = tfno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * 
	 * @author sergiosanchezsanchez
	 *
	 */
	public static class SchoolLocation {
		
		/**
		 * Latitude
		 */
		@Field("latitude")
		private Double latitude;
		
		/**
		 * Longitude
		 */
		@Field("longitude")
		private Double longitude;
		
		public SchoolLocation(){}
		
		/**
		 * 
		 * @param latitude
		 * @param longitude
		 */
		@PersistenceConstructor
		public SchoolLocation(final Double latitude, final Double longitude) {
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
