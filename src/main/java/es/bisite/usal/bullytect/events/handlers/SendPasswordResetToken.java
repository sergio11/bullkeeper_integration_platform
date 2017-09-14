package es.bisite.usal.bullytect.events.handlers;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import es.bisite.usal.bullytect.events.PasswordResetEvent;
import es.bisite.usal.bullytect.service.IMailClient;
import es.bisite.usal.bullytect.service.IParentsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sergio
 */
@Component
public class SendPasswordResetToken implements ApplicationListener<PasswordResetEvent> {
    
    private static Logger logger = LoggerFactory.getLogger(SendPasswordResetToken.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;

    public SendPasswordResetToken(IMailClient mailClient, IParentsService parentService) {
        this.mailClient = mailClient;
        this.parentService = parentService;
    }
    
	@Override
	public void onApplicationEvent(final PasswordResetEvent event) {
		
		Optional.ofNullable(parentService.getParentById(event.getPasswordResetToken().getUser()))
        .ifPresent(parent -> {
            logger.debug("Send mail for reset password token");
            // Send Mail for Activate Account
            logger.debug("Send email to: " + parent.getEmail());
            mailClient.sendMailForResetPassword(parent.getId().toString(), parent.getEmail(), parent.getFirstName(), 
            		parent.getLastName(), event.getPasswordResetToken().getToken(), new Locale(parent.getLocale()));
        });
	}
}
