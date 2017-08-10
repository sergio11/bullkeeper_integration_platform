package sanchez.sanchez.sergio.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;

public class ParentEntity  extends UserSystemEntity {
	
	public ParentEntity(){}

	@PersistenceConstructor
	public ParentEntity(String firstName, String lastName, Integer age, String email, String password,
			AuthorityEntity authority) {
		super(firstName, lastName, age, email, password, authority);
	}
	
}
