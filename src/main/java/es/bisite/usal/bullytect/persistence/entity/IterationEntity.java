package es.bisite.usal.bullytect.persistence.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import es.bisite.usal.bullytect.persistence.utils.CascadeSave;

/**
 *
 * @author sergio
 */
@Document(collection = IterationEntity.COLLECTION_NAME)
public class IterationEntity {
    
    public final static String COLLECTION_NAME = "iterations";

    @Id
    private ObjectId id;
    
    @Field("start_date")
    private Date startDate;
    
    @Field("finish_date")
    private Date finishDate;
    
    @Field("duration")
    private Long duration;
    
    @Field("total_tasks")
    private Integer totalTasks = 0;
    
    @Field("total_failed_tasks")
    private Integer totalFailedTasks = 0;
    
    @Field("total_comments")
    private Integer totalComments = 0;
    
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
    public IterationEntity(Date startDate, Date finishDate, Integer totalTasks, Integer totalFailedTasks,
			Integer totalComments, Set<TaskEntity> tasks) {
		super();
		this.startDate = startDate;
		this.finishDate = finishDate;
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
