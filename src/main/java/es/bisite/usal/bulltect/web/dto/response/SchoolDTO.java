package es.bisite.usal.bulltect.web.dto.response;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolDTO extends ResourceSupport {
	
	@JsonProperty("identity")
	private String identity;
	@JsonProperty("name")
	private String name;
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
	@JsonProperty("email")
	private String email;
	
	public SchoolDTO(){}
	
	public SchoolDTO(String identity, String name, String residence,  Double latitude, Double longitude, String province, Integer tfno,
			String email) {
		super();
		this.identity = identity;
		this.name = name;
		this.residence = residence;
		this.latitude = latitude;
		this.longitude = longitude;
		this.province = province;
		this.tfno = tfno;
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
