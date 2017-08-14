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
import sanchez.sanchez.sergio.rest.exception.NoAlertsFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.AlertResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AlertsErrorController {
	
	private static Logger logger = LoggerFactory.getLogger(AlertsErrorController.class);

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public AlertsErrorController(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(NoAlertsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAlertsFoundException(NoAlertsFoundException noAlertsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(AlertResponseCode.NO_ALERTS_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("alerts.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
}
