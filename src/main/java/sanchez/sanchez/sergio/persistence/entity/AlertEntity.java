package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = AlertEntity.COLLECTION_NAME)
public class AlertEntity {

    public final static String COLLECTION_NAME = "alerts";

    @Id
    private ObjectId id;

    @Field
    private AlertLevelEnum level = AlertLevelEnum.INFO;

    @Field
    private String payload;

    @Field("create_at")
    private Date createAt = new Date();
    
    @Field("delivered_at")
    private Date deliveredAt;
    
    @Field("parent")
    @DBRef
    private ParentEntity parent;

    @Field("son")
    @DBRef
    private SonEntity son;
    
    private Boolean delivered = Boolean.FALSE;

    public AlertEntity() {}

    public AlertEntity(String payload, ParentEntity parent, SonEntity son) {
		super();
		this.payload = payload;
		this.parent = parent;
		this.son = son;
	}

	public AlertEntity(AlertLevelEnum level, String payload, ParentEntity parent, SonEntity son) {
        super();
        this.level = level;
        this.payload = payload;
        this.parent = parent;
        this.son = son;
    }

    @PersistenceConstructor
    public AlertEntity(AlertLevelEnum level, String payload, Date createAt, ParentEntity parent, SonEntity son,
			Boolean delivered) {
		super();
		this.level = level;
		this.payload = payload;
		this.createAt = createAt;
		this.parent = parent;
		this.son = son;
		this.delivered = delivered;
	}

    public ObjectId getId() {
        return id;
    } 

	public void setId(ObjectId id) {
        this.id = id;
    }

    public AlertLevelEnum getLevel() {
        return level;
    }

    public void setLevel(AlertLevelEnum level) {
        this.level = level;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public SonEntity getSon() {
        return son;
    }

    public void setSon(SonEntity son) {
        this.son = son;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}
}
