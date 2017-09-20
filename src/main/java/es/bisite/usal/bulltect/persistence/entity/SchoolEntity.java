package es.bisite.usal.bulltect.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = SchoolEntity.COLLECTION_NAME)
public class SchoolEntity {
	
	public final static String COLLECTION_NAME = "schools";
	
	@Id
    private ObjectId id;
	@Field
	private String name;
	@Field
	private String residence;
	@Field
	private String location;
	@Field
	private String province;
	@Field
	private Integer tfno;
	@Field
	private String email;

	
	public SchoolEntity(){}
	
	@PersistenceConstructor
	public SchoolEntity(String name, String residence, String location, String province, Integer tfno, String email) {
		super();
		this.name = name;
		this.residence = residence;
		this.location = location;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
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

	@Override
	public String toString() {
		return "SchoolEntity [id=" + id + ", name=" + name + ", residence=" + residence + ", location=" + location
				+ ", province=" + province + ", tfno=" + tfno + ", email=" + email + "]";
	}

}
