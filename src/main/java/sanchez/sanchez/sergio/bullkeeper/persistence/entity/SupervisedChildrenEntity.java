package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*
 * Supervised Children
 */
@Document(collection = SupervisedChildrenEntity.COLLECTION_NAME)
public class SupervisedChildrenEntity {
	
	public final static String COLLECTION_NAME = "supervised_children";
	
	/**
	 * Id
	 */
    @Id
    protected ObjectId id;
    
    /**
     * Kid
     */
    @DBRef
    private KidEntity kid;
    
    /**
     * Guardian
     */
    @DBRef
    private GuardianEntity guardian;
    
    /**
     * Role
     */
    @Field("role")
    private GuardianRolesEnum role = GuardianRolesEnum.ADMIN;
    
    /**
     * Is Confirmed
     */
    @Field("is_confirmed")
    private boolean isConfirmed = false;
    
    /**
     * Request At
     */
    @Field("request_at")
    private Date requestAt = new Date();
    
    public SupervisedChildrenEntity() {}
    
    /**
     * 
     * @param kid
     * @param guardian
     */
    @PersistenceConstructor
    public SupervisedChildrenEntity(ObjectId id, KidEntity kid, GuardianEntity guardian, GuardianRolesEnum role,
			boolean isConfirmed, Date requestAt) {
		super();
		this.id = id;
		this.kid = kid;
		this.guardian = guardian;
		this.role = role;
		this.isConfirmed = isConfirmed;
		this.requestAt = requestAt;
	}

	public ObjectId getId() {
		return id;
	}

	public KidEntity getKid() {
		return kid;
	}

	public GuardianEntity getGuardian() {
		return guardian;
	}

	public GuardianRolesEnum getRole() {
		return role;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public Date getRequestAt() {
		return requestAt;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public void setGuardian(GuardianEntity guardian) {
		this.guardian = guardian;
	}

	public void setRole(GuardianRolesEnum role) {
		this.role = role;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public void setRequestAt(Date requestAt) {
		this.requestAt = requestAt;
	}
   
}
