package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SchoolEmailShouldExistsIfPresent;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SchoolNameShouldNotExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidPhoneNumber;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.PhoneNumberDeserializer;


/**
 * Add School DTO
 * @author sergiosanchezsanchez
 *
 */
public class AddSchoolDTO {
	
	/**
	 * Name
	 */
	@NotBlank(message = "{school.name.not.blank}")
    @Size(min = 5, max = 30, message = "{school.name.size}", groups = Extended.class)
	@SchoolNameShouldNotExists(message="{school.name.should.not.exists}", groups = Extended.class)
	@JsonProperty("name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	private String name;
	
	/**
	 * Residence
	 */
	@NotBlank(message = "{school.residence.not.blank}")
    @Size(min = 5, max = 100, message = "{school.residence.size}")
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
     * Telephone
     */
    @ValidPhoneNumber(message = "{school.telephone.not.valid}")
	@JsonProperty("tfno")
	@JsonDeserialize(using = PhoneNumberDeserializer.class)
	private PhoneNumber tfno;
	
	/**
	 * Email
	 */
	@SchoolEmailShouldExistsIfPresent(message="{school.email.not.unique}")
	@Email(message="{school.email.invalid}")
	@JsonProperty("email")
	private String email;
	
	
	public AddSchoolDTO(){}
	
	/**
	 * 
	 * @param name
	 * @param residence
	 * @param latitude
	 * @param longitude
	 * @param province
	 * @param tfno
	 * @param email
	 */
	public AddSchoolDTO(
			final String name, 
			final String residence, 
			final Double latitude, 
			final Double longitude, 
			final String province, 
			final PhoneNumber tfno,
			final String email) {
		super();
		this.name = name;
		this.residence = residence;
		this.latitude = latitude;
		this.longitude = longitude;
		this.province = province;
		this.tfno = tfno;
		this.email = email;
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


	public PhoneNumber getTfno() {
		return tfno;
	}

	public void setTfno(PhoneNumber tfno) {
		this.tfno = tfno;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "AddSchoolDTO [name=" + name + ", residence=" + residence + ", latitude=" + latitude + ", longitude="
				+ longitude + ", province=" + province + ", tfno=" + tfno + ", email=" + email + "]";
	}
	
}
