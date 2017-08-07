package sanchez.sanchez.sergio.dto.response;

import org.springframework.hateoas.ResourceSupport;

public class FieldErrorDTO extends ResourceSupport {
	
	private final String field;
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
