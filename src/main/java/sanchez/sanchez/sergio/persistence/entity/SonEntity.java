package sanchez.sanchez.sergio.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class SonEntity extends UserEntity {
	
	@DBRef
	private SchoolEntity school;
	@DBRef
	private ParentEntity parent;
	
	public SonEntity(){}

	@PersistenceConstructor
	public SonEntity(String firstName, String lastName, Integer age, SchoolEntity school, ParentEntity parent) {
		super(firstName, lastName, age);
		this.school = school;
		this.parent = parent;
	}
	
	public SchoolEntity getSchool() {
		return school;
	}

	public void setSchool(SchoolEntity school) {
		this.school = school;
	}

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}
}
