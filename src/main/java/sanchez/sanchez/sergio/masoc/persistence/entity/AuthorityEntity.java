package sanchez.sanchez.sergio.masoc.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

/**
 * Authority Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = AuthorityEntity.COLLECTION_NAME)
public class AuthorityEntity implements GrantedAuthority {
	
	public final static String COLLECTION_NAME = "authorities";
	
	/**
	 * ID
	 */
	@Id
    private ObjectId id;
	
	/**
	 * Type
	 */
	@Field
    private AuthorityEnum type;
	
	/**
	 * Description
	 */
	@Field
	private String description;
	
	public AuthorityEntity(){}
	
	/**
	 * 
	 * @param type
	 * @param description
	 */
	@PersistenceConstructor
	public AuthorityEntity(AuthorityEnum type, String description) {
		super();
		this.type = type;
		this.description = description;
	}

	public ObjectId getId() {
		return id;
	}

	public AuthorityEnum getType() {
		return type;
	}

	public void setType(AuthorityEnum type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getAuthority() {
		return type.name();
	}

}
