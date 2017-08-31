package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = PasswordResetTokenEntity.COLLECTION_NAME)
public class PasswordResetTokenEntity {
	
	public final static String COLLECTION_NAME = "password_reset_tokens";
	
	private static final int EXPIRATION = 60 * 24;
	
	@Id
	private ObjectId id;
	
	@Field("token")
	private String token;
	
	private ObjectId user;
	
	@Field("expiry_date")
	private Date expiryDate;

	@PersistenceConstructor
	public PasswordResetTokenEntity(String token, ObjectId user, Date expiryDate) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}
	
	public PasswordResetTokenEntity(String token, ObjectId user) {
		super();
		this.token = token;
		this.user = user;
	}
	
	public PasswordResetTokenEntity(){}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ObjectId getUser() {
		return user;
	}

	public void setUser(ObjectId user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
