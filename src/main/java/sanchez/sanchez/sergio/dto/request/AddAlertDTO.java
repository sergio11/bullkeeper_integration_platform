package sanchez.sanchez.sergio.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import sanchez.sanchez.sergio.persistence.constraints.SonShouldExists;
import sanchez.sanchez.sergio.persistence.constraints.ValidAlertLevel;
import sanchez.sanchez.sergio.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.persistence.constraints.group.Extended;

public class AddAlertDTO {
	
	@ValidAlertLevel(message = "{alert.level.invalid}")
	private String level;
	@NotBlank(message = "{alert.payload.notblank}")
    @Size(min = 5, message = "{alert.payload.size}")
	private String payload;
	@ValidObjectId(message = "{son.id.notvalid}")
	@SonShouldExists(message = "{alert.target.not.exists}", groups = Extended.class)
	private String target;
	
	public AddAlertDTO(){}
	
	public AddAlertDTO(String level, String payload, String target) {
		super();
		this.level = level;
		this.payload = payload;
		this.target = target;
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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
