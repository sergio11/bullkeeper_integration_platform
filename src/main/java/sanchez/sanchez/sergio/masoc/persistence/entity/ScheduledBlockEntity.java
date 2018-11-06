package sanchez.sanchez.sergio.masoc.persistence.entity;

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
	@Field("son")
	private SonEntity son;
	
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
	 * @param son
	 */
	@PersistenceConstructor
	public ScheduledBlockEntity(ObjectId id, String name, boolean enable, boolean repeatable, LocalTime startAt,
			LocalTime endAt, int[] weeklyFrequency, SonEntity son) {
		super();
		this.id = id;
		this.name = name;
		this.enable = enable;
		this.repeatable = repeatable;
		this.startAt = startAt;
		this.endAt = endAt;
		this.weeklyFrequency = weeklyFrequency;
		this.son = son;
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

	public SonEntity getSon() {
		return son;
	}

	public void setSon(SonEntity son) {
		this.son = son;
	}
}
