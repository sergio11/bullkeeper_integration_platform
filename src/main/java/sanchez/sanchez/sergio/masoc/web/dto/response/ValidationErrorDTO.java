package sanchez.sanchez.sergio.masoc.web.dto.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationErrorDTO extends ResourceSupport {
	
	@JsonProperty("field_errors")
	private final List<FieldErrorDTO> fieldErrors = new ArrayList<>();
	
	public void addFieldError(String path, String message) {
		FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}
}
