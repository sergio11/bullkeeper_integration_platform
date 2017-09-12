package sanchez.sanchez.sergio.events.handlers;

import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.events.PasswordChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanchez.sanchez.sergio.service.IMailClient;
import sanchez.sanchez.sergio.service.IParentsService;

/**
 *
 * @author sergio
 */
@Component
public class SendEmailToConfirmPasswordChange implements ApplicationListener<PasswordChangedEvent> {
    
    private static Logger logger = LoggerFactory.getLogger(SendEmailToConfirmPasswordChange.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;

    public SendEmailToConfirmPasswordChange(IMailClient mailClient, IParentsService parentService) {
        this.mailClient = mailClient;
        this.parentService = parentService;
    }
    
	@Override
	public void onApplicationEvent(final PasswordChangedEvent event) {
		

		Optional.ofNullable(parentService.getParentById(event.getParentId()))
        .ifPresent(parent -> {
            logger.debug("Send Email To Confirm Password Change");
            mailClient.sendMailForActivateAccount(parent.getEmail(), parent.getFirstName(), parent.getLastName());
        });
	}
}
