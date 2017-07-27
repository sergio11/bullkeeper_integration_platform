package sanchez.sanchez.sergio.models;

import java.util.List;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public class IterationResult {

    private Integer totalTask;
    private Integer taskFailed;
    private List<CommentEntity> comments;
   

    public IterationResult(Integer totalTask, Integer taskFailed, List<CommentEntity> comments) {
        this.totalTask = totalTask;
        this.taskFailed = taskFailed;
        this.comments = comments;
    }

    public Integer getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(Integer totalTask) {
        this.totalTask = totalTask;
    }

    public Integer getTaskFailed() {
        return taskFailed;
    }

    public void setTaskFailed(Integer taskFailed) {
        this.taskFailed = taskFailed;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}
