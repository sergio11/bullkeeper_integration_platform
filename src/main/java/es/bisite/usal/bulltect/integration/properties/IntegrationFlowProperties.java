package es.bisite.usal.bulltect.integration.properties;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class IntegrationFlowProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Value("${poller.integration.flow.fixed.delay}")
	private Long flowFixedDelay;
	@Value("${poller.integration.flow.percentage.social.media}")
	private Long percentageSocialMedia;
	@Value("${poller.integration.flow.min.social.media.per.cycle}")
	private Long minSocialMediaPerCycle;
	
	public IntegrationFlowProperties() {
		super();
	}

	public IntegrationFlowProperties(Long flowFixedDelay, Long percentageSocialMedia, Long minSocialMediaPerCycle) {
		super();
		this.flowFixedDelay = flowFixedDelay;
		this.percentageSocialMedia = percentageSocialMedia;
		this.minSocialMediaPerCycle = minSocialMediaPerCycle;
	}

	public Long getFlowFixedDelay() {
		return flowFixedDelay;
	}
	
	public Long getFlowFixedDelayMillis() {
		return flowFixedDelay * 1000;
	}

	public void setFlowFixedDelay(Long flowFixedDelay) {
		this.flowFixedDelay = flowFixedDelay;
	}

	public Long getPercentageSocialMedia() {
		return percentageSocialMedia;
	}

	public void setPercentageSocialMedia(Long percentageSocialMedia) {
		this.percentageSocialMedia = percentageSocialMedia;
	}

	public Long getMinSocialMediaPerCycle() {
		return minSocialMediaPerCycle;
	}

	public void setMinSocialMediaPerCycle(Long minSocialMediaPerCycle) {
		this.minSocialMediaPerCycle = minSocialMediaPerCycle;
	}
}
