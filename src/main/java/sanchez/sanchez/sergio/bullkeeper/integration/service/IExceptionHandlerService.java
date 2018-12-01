package sanchez.sanchez.sergio.bullkeeper.integration.service;

import org.springframework.messaging.Message;

/**
 *
 * @author sergio
 */
public interface IExceptionHandlerService {
    
    void handleException(Message<Exception> exception);
    
}
