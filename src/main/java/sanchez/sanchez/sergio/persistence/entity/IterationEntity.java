package sanchez.sanchez.sergio.persistence.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sanchez.sanchez.sergio.persistence.utils.CascadeSave;

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
    
    @Field("total_tasks")
    private Integer totalTasks = 0;
    
    @Field("total_failed_tasks")
    private Integer totalFailedTasks = 0;
    
    @Field("total_comments")
    private Integer totalComments = 0;
    
    @Field("tasks")
    @CascadeSave
    private Set<TaskEntity> tasks = new HashSet<>();
    
    
    public IterationEntity(Date startDate, Date finishDate){
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    @PersistenceConstructor
    public IterationEntity(Date startDate, Date finishDate, Integer totalTasks, Integer taskFailed, Integer totalComments, Set<TaskEntity> tasks) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.totalTasks = totalTasks;
        this.totalFailedTasks = taskFailed;
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