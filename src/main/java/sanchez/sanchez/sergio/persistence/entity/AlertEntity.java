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

	
	@Field("son")
    @DBRef
    private SonEntity son;
	
	public AlertEntity(){}
	
	public AlertEntity(String payload, SonEntity sonEntity) {
		super();
		this.payload = payload;
		this.son = sonEntity;
	}
	
	@PersistenceConstructor
	public AlertEntity(AlertLevelEnum level, String payload, Date createAt, SonEntity sonEntity) {
		super();
		this.level = level;
		this.payload = payload;
		this.createAt = createAt;
		this.son = sonEntity;
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

	public SonEntity getSonEntity() {
		return son;
	}

	public void setSonEntity(SonEntity sonEntity) {
		this.son = sonEntity;
	}
}
