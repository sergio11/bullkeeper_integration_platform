package sanchez.sanchez.sergio.masoc.web.dto.request;


import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;


public class SaveDeviceDTO {
	
	@NotBlank(message = "{device.id.notblank}")
	@JsonProperty("device_id")
	private String deviceId;
	
	@NotBlank(message = "{device.registration.token.notblank}")
	@JsonProperty("registration_token")
	private String registrationToken;
	
	
	public SaveDeviceDTO(){}
	

	public SaveDeviceDTO(String deviceId, String registrationToken) {
		super();
		this.deviceId = deviceId;
		this.registrationToken = registrationToken;
	}


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public String getRegistrationToken() {
		return registrationToken;
	}


	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}
}
