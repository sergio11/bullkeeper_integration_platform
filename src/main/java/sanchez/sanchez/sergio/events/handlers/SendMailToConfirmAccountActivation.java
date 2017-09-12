package sanchez.sanchez.sergio.events.handlers;

import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.events.ParentAccountActivatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanchez.sanchez.sergio.service.IMailClient;
import sanchez.sanchez.sergio.service.IParentsService;

/**
 *
 * @author sergio
 */
@Component
public class SendMailToConfirmAccountActivation implements ApplicationListener<ParentAccountActivatedEvent> {
    
    private static Logger logger = LoggerFactory.getLogger(SendMailToConfirmAccountActivation.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;

    public SendMailToConfirmAccountActivation(IMailClient mailClient, IParentsService parentService) {
        this.mailClient = mailClient;
        this.parentService = parentService;
    }
    
	@Override
	public void onApplicationEvent(final ParentAccountActivatedEvent event) {
		Optional.ofNullable(parentService.getParentById(event.getIdentity()))
	        .ifPresent(parent -> {
	            logger.debug("Send Email To Confirm Account Activation");
	            mailClient.sendMailForConfirmAccountActivation(parent.getEmail(), parent.getFirstName(), parent.getLastName());
	        });
	}
}
