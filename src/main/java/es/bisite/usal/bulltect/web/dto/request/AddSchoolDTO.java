package es.bisite.usal.bulltect.web.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.bisite.usal.bulltect.persistence.constraints.SchoolNameShouldNotExists;
import es.bisite.usal.bulltect.persistence.constraints.group.Extended;
import es.bisite.usal.bulltect.web.rest.deserializers.ClearStringDeserializer;

public class AddSchoolDTO {
	
	@NotBlank(message = "{school.name.notblank}")
    @Size(min = 5, max = 15, message = "{school.name.size}")
	@SchoolNameShouldNotExists(message="{school.name.should.not.exists}", groups = Extended.class)
	@JsonProperty("name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
	private String schoolName;
	@NotBlank(message = "{school.residence.notblank}")
    @Size(min = 5, max = 15, message = "{school.residence.size}")
	@JsonProperty("residence")
	private String residence;
	@NotBlank(message = "{school.location.notblank}")
    @Size(min = 5, max = 15, message = "{school.location.size}")
	@JsonProperty("location")
	private String location;
	@JsonProperty("province")
	private String province;
	@JsonProperty("tfno")
	private Integer tfno;
	@Email(message="{school.email.invalid}")
	@JsonProperty("email")
	private String email;
	
	
	public AddSchoolDTO(){}
	
	
	public AddSchoolDTO(String name, String residence, String location, String province, Integer tfno,
			String email) {
		super();
		this.schoolName = name;
		this.residence = residence;
		this.location = location;
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


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
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
