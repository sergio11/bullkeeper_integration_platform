package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 * Alert Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = AlertEntity.COLLECTION_NAME)
public class AlertEntity {

    public final static String COLLECTION_NAME = "alerts";

    /**
     * Id
     */
    @Id
    private ObjectId id;

    /**
     * Level
     */
    @Field
    private AlertLevelEnum level = AlertLevelEnum.INFO;

    /**
     * Title
     */
    @Field("title")
    private String title;

    /**
     * Payload
     */
    @Field("payload")
    private String payload;

    /**
     * Create At
     */
    @Field("create_at")
    private Date createAt = new Date();

    /**
     * Delivered At
     */
    @Field("delivered_at")
    private Date deliveredAt;

    /**
     * Delivery Mode
     */
    @Field("delivery_mode")
    private AlertDeliveryModeEnum deliveryMode = AlertDeliveryModeEnum.PUSH_NOTIFICATION;
    
    /**
     * Category
     */
    @Field("category")
    private AlertCategoryEnum category = AlertCategoryEnum.DEFAULT;

    /**
     * Guardian
     */
    @Field("guardian")
    @DBRef
    private GuardianEntity guardian;

    /**
     * Kid
     */
    @Field("kid")
    @DBRef
    private KidEntity kid;

    /**
     * Delivered
     */
    private Boolean delivered = Boolean.FALSE;

    public AlertEntity() {
    }

    /**
     * 
     * @param title
     * @param payload
     * @param guardian
     * @param kid
     */
    public AlertEntity(String title, String payload, GuardianEntity guardian, KidEntity kid) {
        super();
        this.title = title;
        this.payload = payload;
        this.guardian = guardian;
        this.kid = kid;
    }
    
    /**
     * 
     * @param level
     * @param title
     * @param payload
     * @param guardian
     * @param kid
     */
    public AlertEntity(AlertLevelEnum level, String title, String payload, GuardianEntity guardian, KidEntity kid) {
        super();
        this.level = level;
        this.title = title;
        this.payload = payload;
        this.guardian = guardian;
        this.kid = kid;
    }
    
    /**
     * 
     * @param level
     * @param title
     * @param payload
     * @param guardian
     * @param kid
     * @param category
     */
    public AlertEntity(AlertLevelEnum level, String title, String payload, GuardianEntity guardian, KidEntity kid, AlertCategoryEnum category) {
        super();
        this.level = level;
        this.title = title;
        this.payload = payload;
        this.guardian = guardian;
        this.kid = kid;
        this.category = category;
    }

    /**
     * 
     * @param level
     * @param title
     * @param payload
     * @param guardian
     * @param kid
     * @param deliveryMode
     */
    public AlertEntity(AlertLevelEnum level, String title, String payload, GuardianEntity guardian, KidEntity kid, AlertDeliveryModeEnum deliveryMode) {
        super();
        this.level = level;
        this.title = title;
        this.payload = payload;
        this.guardian = guardian;
        this.kid = kid;
        this.deliveryMode = deliveryMode;
    }

    /**
     * 
     * @param level
     * @param title
     * @param payload
     * @param createAt
     * @param guardian
     * @param kid
     * @param delivered
     * @param deliveryMode
     * @param category
     */
    @PersistenceConstructor
    public AlertEntity(AlertLevelEnum level, String title, String payload, Date createAt, GuardianEntity guardian, KidEntity kid,
            Boolean delivered, AlertDeliveryModeEnum deliveryMode, AlertCategoryEnum category) {
        super();
        this.level = level;
        this.title = title;
        this.payload = payload;
        this.createAt = createAt;
        this.guardian = guardian;
        this.kid = kid;
        this.delivered = delivered;
        this.deliveryMode = deliveryMode;
        this.category = category;
    }

	public ObjectId getId() {
		return id;
	}

	public AlertLevelEnum getLevel() {
		return level;
	}

	public String getTitle() {
		return title;
	}

	public String getPayload() {
		return payload;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public AlertDeliveryModeEnum getDeliveryMode() {
		return deliveryMode;
	}

	public AlertCategoryEnum getCategory() {
		return category;
	}

	public GuardianEntity getGuardian() {
		return guardian;
	}

	public KidEntity getKid() {
		return kid;
	}

	public Boolean getDelivered() {
		return delivered;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setLevel(AlertLevelEnum level) {
		this.level = level;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public void setDeliveryMode(AlertDeliveryModeEnum deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public void setCategory(AlertCategoryEnum category) {
		this.category = category;
	}

	public void setGuardian(GuardianEntity guardian) {
		this.guardian = guardian;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

    
    
}
