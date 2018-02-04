package es.bisite.usal.bulltect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Resource Not Found")
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

}
