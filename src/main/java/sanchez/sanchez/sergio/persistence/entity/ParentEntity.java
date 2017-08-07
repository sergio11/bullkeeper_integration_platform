package sanchez.sanchez.sergio.persistence.entity;


import java.util.Set;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import sanchez.sanchez.sergio.persistence.utils.CascadeSave;

public class ParentEntity  extends UserEntity {
	
	@Field("email")
	private String email;
	@Field("password")
	private String password;
	@Field("children")
    @CascadeSave
    private Set<UserEntity> children;
	
	
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

	public Set<UserEntity> getChildren() {
		return children;
	}

	public void addChildren(UserEntity children) {
		this.children.add(children);
	}
	
}
