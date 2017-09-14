package es.bisite.usal.bullytect.events.handlers;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import es.bisite.usal.bullytect.events.ParentAccountActivatedEvent;
import es.bisite.usal.bullytect.service.IMailClient;
import es.bisite.usal.bullytect.service.IParentsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	            mailClient.sendMailForConfirmAccountActivation(parent.getEmail(), parent.getFirstName(), 
	            		parent.getLastName(), new Locale(parent.getLocale()));
	        });
	}
}
