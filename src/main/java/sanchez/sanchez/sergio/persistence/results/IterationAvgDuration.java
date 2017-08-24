package sanchez.sanchez.sergio.persistence.results;

import org.springframework.data.mongodb.core.mapping.Field;

public class IterationAvgDuration {
	
	@Field("avg_duration") 
	private Double avgDuration;

	public Double getAvgDuration() {
		return avgDuration;
	}

	public void setAvgDuration(Double avgDuration) {
		this.avgDuration = avgDuration;
	}
}
