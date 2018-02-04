package es.bisite.usal.bulltect.web.dto.response;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldErrorDTO extends ResourceSupport {
	
	@JsonProperty("field")
	private final String field;
	@JsonProperty("message")
    private final String message;
    
    
	public FieldErrorDTO(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
   
}
