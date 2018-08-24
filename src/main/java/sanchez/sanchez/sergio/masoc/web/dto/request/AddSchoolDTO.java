package sanchez.sanchez.sergio.masoc.web.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import sanchez.sanchez.sergio.masoc.persistence.constraints.SchoolNameShouldNotExists;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.masoc.web.rest.deserializers.ClearStringDeserializer;

public class AddSchoolDTO {
	
	@NotBlank(message = "{school.name.notblank}")
    @Size(min = 5, max = 30, message = "{school.name.size}")
	@SchoolNameShouldNotExists(message="{school.name.should.not.exists}", groups = Extended.class)
	@JsonProperty("name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	private String schoolName;
	@NotBlank(message = "{school.residence.notblank}")
    @Size(min = 5, max = 100, message = "{school.residence.size}")
	@JsonProperty("residence")
	private String residence;
	@JsonProperty("latitude")
	private Double latitude;
	@JsonProperty("longitude")
	private Double longitude;
	@JsonProperty("province")
	private String province;
	@JsonProperty("tfno")
	private Integer tfno;
	@Email(message="{school.email.invalid}")
	@JsonProperty("email")
	private String email;
	
	
	public AddSchoolDTO(){}
	
	
	public AddSchoolDTO(String name, String residence,  Double latitude, Double longitude, String province, Integer tfno,
			String email) {
		super();
		this.schoolName = name;
		this.residence = residence;
		this.latitude = latitude;
		this.longitude = longitude;
		this.province = province;
		this.tfno = tfno;
		this.email = email;
	}


	public String getName() {
		return schoolName;
	}


	public void setName(String name) {
		this.schoolName = name;
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


	public Integer getTfno() {
		return tfno;
	}


	public void setTfno(Integer tfno) {
		this.tfno = tfno;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
}
