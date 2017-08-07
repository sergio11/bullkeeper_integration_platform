package sanchez.sanchez.sergio.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class SonEntity extends UserEntity {
	
	@DBRef
	private SchoolEntity school;

	@PersistenceConstructor
	public SonEntity(String firstName, String lastName, Integer age, SchoolEntity school) {
		super(firstName, lastName, age);
		this.school = school;
	}

	public SchoolEntity getSchool() {
		return school;
	}

	public void setSchool(SchoolEntity school) {
		this.school = school;
	}

}
