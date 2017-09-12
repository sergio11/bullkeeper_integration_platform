package sanchez.sanchez.sergio.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class AddSchoolDTO {
	
	@NotBlank(message = "{school.name.notblank}")
    @Size(min = 5, max = 15, message = "{school.name.size}")
	private String name;
	@NotBlank(message = "{school.residence.notblank}")
    @Size(min = 5, max = 15, message = "{school.residence.size}")
	private String residence;
	@NotBlank(message = "{school.location.notblank}")
    @Size(min = 5, max = 15, message = "{school.location.size}")
	private String location;
	private String province;
	private Integer tfno;
	@Email(message="{school.email.invalid}")
	private String email;
	
	
	public AddSchoolDTO(String name, String residence, String location, String province, Integer tfno,
			String email) {
		super();
		this.name = name;
		this.residence = residence;
		this.location = location;
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
