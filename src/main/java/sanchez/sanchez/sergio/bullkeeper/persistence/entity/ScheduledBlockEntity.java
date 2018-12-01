package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.joda.time.LocalTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Scheduled Block 
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = ScheduledBlockEntity.COLLECTION_NAME)
public class ScheduledBlockEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "scheduled_blocks";
	
	@Id
    private ObjectId id;
	
	/**
	 * Name
	 */
	@Field("name")
    private String name;
	
	/**
	 * Enable
	 */
	@Field("enable")
    private boolean enable;
	
	/**
	 * Repeatable
	 */
	@Field("repeatable")
    private boolean repeatable;
	
	/**
	 * Start at
	 */
	@Field("start_at")
    private LocalTime startAt;
	
	/**
	 * End at
	 */
	@Field("end_at")
    private LocalTime endAt;
	
	/**
	 * Weekly Frequency
	 */
	@Field("weekly_frequency")
	private int[] weeklyFrequency;
	
	/**
	 * Son
	 */
	@DBRef
	@Field("kid")
	private KidEntity kid;
	
	/**
	 * Image
	 */
	@Field("image")
	private String image;
	
	/**
	 * 
	 */
	public ScheduledBlockEntity() {}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param enable
	 * @param repeatable
	 * @param startAt
	 * @param endAt
	 * @param weeklyFrequency
	 * @param kid
	 * @param image
	 */
	@PersistenceConstructor
	public ScheduledBlockEntity(ObjectId id, String name, boolean enable, boolean repeatable, LocalTime startAt,
			LocalTime endAt, int[] weeklyFrequency, KidEntity kid, final String image) {
		super();
		this.id = id;
		this.name = name;
		this.enable = enable;
		this.repeatable = repeatable;
		this.startAt = startAt;
		this.endAt = endAt;
		this.weeklyFrequency = weeklyFrequency;
		this.kid = kid;
		this.image = image;
	}

	public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isEnable() {
		return enable;
	}

	public boolean isRepeatable() {
		return repeatable;
	}

	public LocalTime getStartAt() {
		return startAt;
	}

	public LocalTime getEndAt() {
		return endAt;
	}

	public int[] getWeeklyFrequency() {
		return weeklyFrequency;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	public void setStartAt(LocalTime startAt) {
		this.startAt = startAt;
	}

	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}

	public void setWeeklyFrequency(int[] weeklyFrequency) {
		this.weeklyFrequency = weeklyFrequency;
	}

	

	public KidEntity getKid() {
		return kid;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
