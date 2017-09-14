package es.bisite.usal.bullytect.events.handlers;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import es.bisite.usal.bullytect.events.ParentRegistrationByFacebookSuccessEvent;
import es.bisite.usal.bullytect.service.IMailClient;
import es.bisite.usal.bullytect.service.IParentsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sergio
 */
@Component
public class SendEmailToConfirmRegistrationViaFacebook implements ApplicationListener<ParentRegistrationByFacebookSuccessEvent> {
    
    private static Logger logger = LoggerFactory.getLogger(SendEmailToConfirmRegistrationViaFacebook.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;

    public SendEmailToConfirmRegistrationViaFacebook(IMailClient mailClient, IParentsService parentService) {
        this.mailClient = mailClient;
        this.parentService = parentService;
    }


	@Override
	public void onApplicationEvent(ParentRegistrationByFacebookSuccessEvent event) {
		Optional.ofNullable(parentService.getParentById(event.getIdentity()))
        .ifPresent(parent -> {
            logger.debug("Send Email To Confirm Registration via Facebook");
            mailClient.sendMailForConfirmRegistrationViaFacebook(parent.getEmail(), parent.getFirstName(), 
            		parent.getLastName(), new Locale(parent.getLocale()));
        });
	}
}
