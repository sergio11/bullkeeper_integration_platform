package sanchez.sanchez.sergio.service;

import org.springframework.messaging.Message;

/**
 *
 * @author sergio
 */
public interface IExceptionHandlerService {
    
    void handleException(Message<Exception> exception);
    
}
