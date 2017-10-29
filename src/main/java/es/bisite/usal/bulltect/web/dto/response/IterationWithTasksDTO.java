package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sergio
 */
public class IterationWithTasksDTO implements Serializable {
    

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("start_date")
    private String startDate;
	@JsonProperty("finish_date")
    private Date finishDate;
	@JsonProperty("total_tasks")
    private Integer totalTasks;
	@JsonProperty("total_failed_tasks")
    private Integer totalFailedTasks;
	@JsonProperty("total_comments")
    private Integer totalComments;
	@JsonProperty("tasks")
	private Set<TaskDTO> tasks;
	

    public IterationWithTasksDTO(){}


	public IterationWithTasksDTO(String startDate, Date finishDate, Integer totalTasks, Integer totalFailedTasks,
			Integer totalComments, Set<TaskDTO> tasks) {
		super();
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.totalTasks = totalTasks;
		this.totalFailedTasks = totalFailedTasks;
		this.totalComments = totalComments;
		this.tasks = tasks;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
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


	public Set<TaskDTO> getTasks() {
		return tasks;
	}


	public void setTasks(Set<TaskDTO> tasks) {
		this.tasks = tasks;
	}
    
    
}
