package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidAlertLevel;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;

public class AddAlertDTO {
	
	/**
	 * Level
	 */
	@ValidAlertLevel(message = "{alert.level.invalid}")
	@JsonProperty("level")
	private String level;
	
	/**
	 * Payload
	 */
	@NotBlank(message = "{alert.payload.not.blank}")
    @Size(min = 5, message = "{alert.payload.size}")
	@JsonProperty("payload")
	private String payload;
	
	/**
	 * Target
	 */
	@ValidObjectId(message = "{id.not.valid}")
	@KidShouldExists(message = "{alert.target.not.exists}", groups = Extended.class)
	@JsonProperty("target")
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
