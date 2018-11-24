package sanchez.sanchez.sergio.masoc.persistence.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.masoc.persistence.utils.CascadeSave;

/**
 *
 * @author sergio
 */
@Document(collection = IterationEntity.COLLECTION_NAME)
public class IterationEntity {
    
    public final static String COLLECTION_NAME = "iterations";

    /**
     * Id
     */
    @Id
    private ObjectId id;
    
    /**
     * Start Date
     */
    @Field("start_date")
    private Date startDate;
    
    /**
     * Finish Date
     */
    @Field("finish_date")
    private Date finishDate;
    
    /**
     * Duration
     */
    @Field("duration")
    private Long duration;
    
    /**
     * Total Tasks
     */
    @Field("total_tasks")
    private Integer totalTasks = 0;
    
    /**
     * Total Failed Tasks
     */
    @Field("total_failed_tasks")
    private Integer totalFailedTasks = 0;
    
    /**
     * Total Comments
     */
    @Field("total_comments")
    private Integer totalComments = 0;
    
    /**
     * Tasks
     */
    @Field("tasks")
    @DBRef
    @CascadeSave
    private Set<TaskEntity> tasks = new HashSet<>();
   
    
    public IterationEntity(){}
    
    public IterationEntity(Date startDate, Date finishDate, Long duration){
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
    }
   
    @PersistenceConstructor
    public IterationEntity(ObjectId id, Date startDate, Date finishDate, Long duration, Integer totalTasks,
			Integer totalFailedTasks, Integer totalComments, Set<TaskEntity> tasks) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.duration = duration;
		this.totalTasks = totalTasks;
		this.totalFailedTasks = totalFailedTasks;
		this.totalComments = totalComments;
		this.tasks = tasks;
	}
    
    public ObjectId getId() {
        return id;
    }

	
	public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
   
    
    public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Integer getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Integer totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Integer getTotalFailedTasks() {
        return totalFailedTasks;
    }

    public void setTotalFailedTasks(Integer totalFailedTasks) {
        this.totalFailedTasks = totalFailedTasks;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public void addTask(TaskEntity task) {
        this.tasks.add(task);
    }
}
