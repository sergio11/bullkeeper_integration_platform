package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ParentEntity.COLLECTION_NAME)
public final class ParentEntity extends UserSystemEntity {
	
	public final static String COLLECTION_NAME = "parents";

    public ParentEntity() {
    }

    @PersistenceConstructor
    public ParentEntity(String firstName, String lastName, Date birthdate, String email, String password,
			String passwordRequestedAt, Boolean active, Boolean locked, Date lastLoginAccess, String confirmationToken,
			AuthorityEntity authority) {
		super(firstName, lastName, birthdate, email, password, passwordRequestedAt, active, locked, lastLoginAccess,
				confirmationToken, authority);
	}
    
    
    public ParentEntity(String firstName, String lastName, Date birthdate, String email, String password, AuthorityEntity authority) {
        super(firstName, lastName, birthdate, email, password, authority);
    }
	
}
