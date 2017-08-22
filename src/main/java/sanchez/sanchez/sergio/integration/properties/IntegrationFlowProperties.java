package sanchez.sanchez.sergio.integration.properties;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class IntegrationFlowProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Value("${poller.integration.flow.time}")
	private Long pollerTime;
	@Value("${poller.integration.flow.percentage.tasks}")
	private Long percentageTasks;
	
	public IntegrationFlowProperties() {
		super();
	}
	
	public IntegrationFlowProperties(Long pollerTime, Long percentageTasks) {
		super();
		this.pollerTime = pollerTime;
		this.percentageTasks = percentageTasks;
	}
	
	public Long getPollerTime() {
		return pollerTime;
	}
	
	public void setPollerTime(Long pollerTime) {
		this.pollerTime = pollerTime;
	}
	
	public Long getPercentageTasks() {
		return percentageTasks;
	}
	
	public void setPercentageTasks(Long percentageTasks) {
		this.percentageTasks = percentageTasks;
	}
}
