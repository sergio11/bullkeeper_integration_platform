

package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */
@Document(collection = UserSystemEntity.COLLECTION_NAME)
public class UserSystemEntity extends PersonEntity {
	
	public final static String COLLECTION_NAME = "users";

    @Field("email")
    protected String email;

    @Field("password")
    protected String password;

    @Field("is_locked")
    protected Boolean locked = Boolean.FALSE;

    @Field("last_login_access")
    protected Date lastLoginAccess;

    @DBRef
    protected AuthorityEntity authority;

    public UserSystemEntity() {
    }

    @PersistenceConstructor
    public UserSystemEntity(String firstName, String lastName, Integer age, String email, String password, AuthorityEntity authority) {
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

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getLastLoginAccess() {
        return lastLoginAccess;
    }

    public void setLastLoginAccess(Date lastLoginAccess) {
        this.lastLoginAccess = lastLoginAccess;
    }

    public AuthorityEntity getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityEntity authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "UserSystemEntity [email=" + email + ", password=" + password + ", locked=" + locked + ", authority="
                + authority + "]";
    }
}
