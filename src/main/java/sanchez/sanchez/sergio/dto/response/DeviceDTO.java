package sanchez.sanchez.sergio.dto.response;

import org.springframework.hateoas.ResourceSupport;

public class DeviceDTO extends ResourceSupport {

	private String registrationToken;
	private String type;
	private String createAt;
	
	public DeviceDTO(){}
	
	public DeviceDTO(String registrationToken, String type, String createAt) {
		super();
		this.registrationToken = registrationToken;
		this.type = type;
		this.createAt = createAt;
	}

	public String getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

}
