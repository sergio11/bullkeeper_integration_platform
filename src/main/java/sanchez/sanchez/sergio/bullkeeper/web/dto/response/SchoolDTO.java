package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class SchoolDTO extends ResourceSupport {
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Name
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * Residence
	 */
	@JsonProperty("residence")
	private String residence;
	
	/**
	 * Latitude
	 */
	@JsonProperty("latitude")
	private Double latitude;
	
	/**
	 * Longitude
	 */
	@JsonProperty("longitude")
	private Double longitude;
	
	/**
	 * Province
	 */
	@JsonProperty("province")
	private String province;
	
	 /**
     * Phone Prefix
     */
    @JsonProperty("phone_prefix")
    private String phonePrefix;
    
    /**
     * Phone Number 
     */
    @JsonProperty("phone_number")
    private Long phoneNumber;
	
	/**
	 * Email
	 */
	@JsonProperty("email")
	private String email;
	
	public SchoolDTO(){}
	
	/**
	 * 
	 * @param identity
	 * @param name
	 * @param residence
	 * @param latitude
	 * @param longitude
	 * @param province
	 * @param phonePrefix
	 * @param phoneNumber
	 * @param email
	 */
	public SchoolDTO(
			final String identity, 
			final String name, 
			final String residence, 
			final Double latitude, 
			final Double longitude, 
			final String province,
			final String phonePrefix,
			final Long phoneNumber,
			final String email) {
		super();
		this.identity = identity;
		this.name = name;
		this.residence = residence;
		this.latitude = latitude;
		this.longitude = longitude;
		this.province = province;
		this.phonePrefix = phonePrefix;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}


	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResidence() {
		return residence;
	}


	public void setResidence(String residence) {
		this.residence = residence;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}

	public String getPhonePrefix() {
		return phonePrefix;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhonePrefix(String phonePrefix) {
		this.phonePrefix = phonePrefix;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "SchoolDTO [identity=" + identity + ", name=" + name + ", residence=" + residence + ", latitude="
				+ latitude + ", longitude=" + longitude + ", province=" + province + ", phonePrefix=" + phonePrefix
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}
	
	
}
