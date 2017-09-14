package es.bisite.usal.bullytect.events.handlers;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import es.bisite.usal.bullytect.events.PasswordChangedEvent;
import es.bisite.usal.bullytect.service.IMailClient;
import es.bisite.usal.bullytect.service.IParentsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sergio
 */
@Component
public class SendMailToConfirmPasswordChange implements ApplicationListener<PasswordChangedEvent> {
    
    private static Logger logger = LoggerFactory.getLogger(SendMailToConfirmPasswordChange.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;

    public SendMailToConfirmPasswordChange(IMailClient mailClient, IParentsService parentService) {
        this.mailClient = mailClient;
        this.parentService = parentService;
    }
    
	@Override
	public void onApplicationEvent(final PasswordChangedEvent event) {
		

		Optional.ofNullable(parentService.getParentById(event.getParentId()))
        .ifPresent(parent -> {
            logger.debug("Send Email To Confirm Password Change");
            mailClient.sendMailForConfirmPasswordChange(parent.getEmail(), parent.getFirstName(), 
            		parent.getLastName(), new Locale(parent.getLocale()));
        });
	}
}
