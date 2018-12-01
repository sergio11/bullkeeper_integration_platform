package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import sanchez.sanchez.sergio.bullkeeper.exception.NoSchoolsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SchoolNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.SchoolResponseCode;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SchoolErrorController extends BaseController {
	
	/**
	 * 
	 * @param schoolNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(SchoolNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSchoolNotFoundException(
    		final SchoolNotFoundException schoolNotFound, final HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(SchoolResponseCode.SCHOOL_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("school.not.found"));
    }
	
    /**
     * 
     * @param noSchoolsFoundException
     * @param request
     * @return
     */
	@ExceptionHandler(NoSchoolsFoundException.class)
	@ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSchoolsFoundException(
    		final NoSchoolsFoundException noSchoolsFoundException, final HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(SchoolResponseCode.NO_SCHOOLS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("schools.not.found"));
    }
	
	@PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }

}
