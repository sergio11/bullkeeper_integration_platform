package sanchez.sanchez.sergio.dto.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class ValidationErrorDTO extends ResourceSupport {
	
	private final List<FieldErrorDTO> fieldErrors = new ArrayList<>();
	
	public void addFieldError(String path, String message) {
		FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}
}
