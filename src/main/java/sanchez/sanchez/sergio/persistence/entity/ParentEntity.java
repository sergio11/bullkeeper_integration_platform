package sanchez.sanchez.sergio.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

public class ParentEntity  extends UserEntity {
	
	@Field("email")
	private String email;
	@Field("password")
	private String password;
	
	public ParentEntity(){}
	
	@PersistenceConstructor
	public ParentEntity(String firstName, String lastName, Integer age, String email, String password) {
		super(firstName, lastName, age);
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
