package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = ParentEntity.COLLECTION_NAME)
public final class ParentEntity extends UserSystemEntity {
	
	public final static String COLLECTION_NAME = "parents";
	
	@Field("telephone")
	private String telephone;

    public ParentEntity() {
    }

    @PersistenceConstructor
    public ParentEntity(String firstName, String lastName, Date birthdate, String email, String password,
			String passwordRequestedAt, Boolean active, Boolean locked, Date lastLoginAccess, String confirmationToken,
			AuthorityEntity authority, String telephone) {
		super(firstName, lastName, birthdate, email, password, passwordRequestedAt, active, locked, lastLoginAccess,
				confirmationToken, authority);
		this.telephone = telephone;
	}
    
    
    public ParentEntity(String firstName, String lastName, Date birthdate, String email, 
    		String password, AuthorityEntity authority) {
        super(firstName, lastName, birthdate, email, password, authority);
    }

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
