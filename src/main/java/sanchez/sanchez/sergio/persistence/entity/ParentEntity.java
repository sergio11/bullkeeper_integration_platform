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
    public ParentEntity(String email, String password, String passwordRequestedAt, Date lastLoginAccess, String confirmationToken, AuthorityEntity authority, String firstName, String lastName, Integer age) {
        super(email, password, passwordRequestedAt, lastLoginAccess, confirmationToken, authority, firstName, lastName, age);
    }
    
    public ParentEntity(String firstName, String lastName, Integer age, String email, String password, AuthorityEntity authority) {
        super(firstName, lastName, age, email, password, authority);
    }
}
