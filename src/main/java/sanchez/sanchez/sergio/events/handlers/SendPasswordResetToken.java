package sanchez.sanchez.sergio.events.handlers;

import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.events.PasswordResetEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanchez.sanchez.sergio.service.IMailClient;
import sanchez.sanchez.sergio.service.IParentsService;
import sanchez.sanchez.sergio.service.ITokenGeneratorService;

/**
 *
 * @author sergio
 */
@Component
public class SendPasswordResetToken implements ApplicationListener<PasswordResetEvent> {
    
    private static Logger logger = LoggerFactory.getLogger(SendPasswordResetToken.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;
    private final ITokenGeneratorService tokenGeneratorService;

    public SendPasswordResetToken(IMailClient mailClient, IParentsService parentService, 
            ITokenGeneratorService tokenGeneratorService) {
        this.mailClient = mailClient;
        this.parentService = parentService;
        this.tokenGeneratorService = tokenGeneratorService;
    }
    
	@Override
	public void onApplicationEvent(final PasswordResetEvent event) {
		
		Optional.ofNullable(parentService.getParentById(event.getPasswordResetToken().getUser()))
        .ifPresent(parent -> {
            logger.debug("Send mail for reset password token");
            // Send Mail for Activate Account
            logger.debug("Send email to: " + parent.getEmail());
            
            
            mailClient.sendMailForActivateAccount(parent.getEmail(), parent.getFirstName(), parent.getLastName());
        });
	}
}
