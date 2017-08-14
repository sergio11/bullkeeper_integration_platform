package sanchez.sanchez.sergio.dto.response;

import org.springframework.hateoas.ResourceSupport;

public class AlertDTO extends ResourceSupport {
	
	private String identity;
	private String level;
	private String payload;
	private String createAt;
	private SonDTO son;
	
	public AlertDTO(){}
	
	public AlertDTO(String identity, String level, String payload, String createAt, SonDTO son) {
		super();
		this.identity = identity;
		this.level = level;
		this.payload = payload;
		this.createAt = createAt;
		this.son = son;
	}

	public String getIdentity() {
		return identity;
	}
	
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	
	public SonDTO getSon() {
		return son;
	}
	
	public void setSon(SonDTO son) {
		this.son = son;
	}
}
