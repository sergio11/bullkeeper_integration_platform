

package sanchez.sanchez.sergio.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */
public class UserSystemEntity extends PersonEntity {
    
    
	@Field("email")
	private String email;
	
	@Field("password")
	private String password;
    
    @DBRef
    private AuthorityEntity authority;
    
    public UserSystemEntity(){}

    @PersistenceConstructor
	public UserSystemEntity(String firstName, String lastName, Integer age, String email, String password,
			AuthorityEntity authority) {
		super(firstName, lastName, age);
		this.email = email;
		this.password = password;
		this.authority = authority;
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

	public AuthorityEntity getAuthority() {
		return authority;
	}

	public void setAuthority(AuthorityEntity authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "UserSystemEntity [email=" + email + ", password=" + password + ", authority=" + authority + "]";
	}
}
