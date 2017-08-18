package sanchez.sanchez.sergio.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ParentEntity.COLLECTION_NAME)
public final class ParentEntity extends UserSystemEntity {
	
	public final static String COLLECTION_NAME = "parents";

    public ParentEntity() {
    }

    @PersistenceConstructor
    public ParentEntity(String firstName, String lastName, Integer age, String email, String password,
            AuthorityEntity authority) {
        super(firstName, lastName, age, email, password, authority);
    }

}
