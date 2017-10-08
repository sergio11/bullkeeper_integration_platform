package es.bisite.usal.bulltect.web.dto.response;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolNameDTO extends ResourceSupport {
	
	@JsonProperty("identity")
	private String identity;
	@JsonProperty("name")
	private String name;
	
	public SchoolNameDTO(){}
	
	public SchoolNameDTO(String identity, String name) {
		super();
		this.identity = identity;
		this.name = name;
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

}
