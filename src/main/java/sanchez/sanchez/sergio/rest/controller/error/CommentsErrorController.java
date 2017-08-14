package sanchez.sanchez.sergio.rest.controller.error;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.CommentNotFoundException;
import sanchez.sanchez.sergio.rest.exception.NoCommentsFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommentResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommentsErrorController {
	
	private static Logger logger = LoggerFactory.getLogger(CommentsErrorController.class);

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public CommentsErrorController(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(NoCommentsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommentsFoundException(NoCommentsFoundException noCommentsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(CommentResponseCode.COMMENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("comments.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
    
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentNotFoundException(CommentNotFoundException commentNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(CommentResponseCode.COMMENT_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("comment.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
}
