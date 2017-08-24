package sanchez.sanchez.sergio.persistence.results;

import org.springframework.data.mongodb.core.mapping.Field;

public class IterationAvgAndTotalDuration {
	
	@Field("total_duration") 
	private Long duration;
	
	@Field("avg_duration") 
	private Double avgDuration;

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Double getAvgDuration() {
		return avgDuration;
	}

	public void setAvgDuration(Double avgDuration) {
		this.avgDuration = avgDuration;
	}
}
