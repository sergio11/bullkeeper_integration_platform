package sanchez.sanchez.sergio.events.handlers;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.events.ParentRegistrationByFacebookSuccessEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanchez.sanchez.sergio.service.IMailClient;
import sanchez.sanchez.sergio.service.IParentsService;

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
