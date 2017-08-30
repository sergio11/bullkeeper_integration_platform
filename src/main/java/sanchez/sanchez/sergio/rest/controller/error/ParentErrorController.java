package sanchez.sanchez.sergio.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.controller.BaseController;
import sanchez.sanchez.sergio.rest.exception.NoChildrenFoundForParentException;
import sanchez.sanchez.sergio.rest.exception.NoChildrenFoundForSelfParentException;
import sanchez.sanchez.sergio.rest.exception.NoParentsFoundException;
import sanchez.sanchez.sergio.rest.exception.ParentNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.ParentResponseCode;
import sanchez.sanchez.sergio.service.IMessageSourceResolver;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ParentErrorController extends BaseController{
	
    private static Logger logger = LoggerFactory.getLogger(ParentErrorController.class);

    @ExceptionHandler(ParentNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleParentNotFoundException(ParentNotFoundException parentNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(ParentResponseCode.PARENT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("parent.not.found"));
    }
    
    @ExceptionHandler(NoChildrenFoundForSelfParentException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundForSelfParentException(NoChildrenFoundForSelfParentException noChildrenFoundForSelfParentException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponse(ParentResponseCode.NO_CHILDREN_FOUND_FOR_SELF_PARENT, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("self.parent.not.children.found"));
    }
    
    @ExceptionHandler(NoChildrenFoundForParentException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundForParentException(NoChildrenFoundForParentException noChildrenFoundForParentException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponse(ParentResponseCode.NO_CHILDREN_FOUND_FOR_PARENT, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("parent.not.children.found"));
    }
    
    @ExceptionHandler(NoParentsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoParentsFoundException(NoParentsFoundException noParentsFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponse(ParentResponseCode.PARENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("parents.not.found"));
    }
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
