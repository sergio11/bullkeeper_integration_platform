package sanchez.sanchez.sergio.events.handlers;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.events.ParentRegistrationSuccessEvent;
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
public class SendMailForActivateAccount implements ApplicationListener<ParentRegistrationSuccessEvent> {
    
    private static Logger logger = LoggerFactory.getLogger(SendMailForActivateAccount.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;
    private final ITokenGeneratorService tokenGeneratorService;

    public SendMailForActivateAccount(IMailClient mailClient, IParentsService parentService, 
            ITokenGeneratorService tokenGeneratorService) {
        this.mailClient = mailClient;
        this.parentService = parentService;
        this.tokenGeneratorService = tokenGeneratorService;
    }
    
    @Override
    public void onApplicationEvent(ParentRegistrationSuccessEvent event) {
        
    	logger.debug("Handle Event: ParentRegistrationSuccessEvent ");
    	
        Optional.ofNullable(parentService.getParentById(event.getIdentity()))
                .ifPresent(parent -> {
                    logger.debug("Save Confirmation token for user");
                    String confirmationToken = tokenGeneratorService.generateToken(parent.getFirstName());
                    // Set Account as inactive and save activation token
                    parentService.setAsNotActiveAndConfirmationToken(parent.getIdentity(), confirmationToken);
                    // Send Mail for Activate Account
                    logger.debug("Send email to: " + parent.getEmail());
                    mailClient.sendMailForActivateAccount(parent.getEmail(), parent.getFirstName(), 
                    		parent.getLastName(), confirmationToken, new Locale(parent.getLocale()));
                });
        
    }
}
