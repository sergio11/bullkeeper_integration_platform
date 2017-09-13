package sanchez.sanchez.sergio.events.handlers;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.events.AccountDeletionRequestEvent;
import sanchez.sanchez.sergio.service.IMailClient;
import sanchez.sanchez.sergio.service.IParentsService;
import sanchez.sanchez.sergio.service.ITokenGeneratorService;

@Component
public class SendEmailToCompleteAccountDeletionProcess  implements ApplicationListener<AccountDeletionRequestEvent>{
	
	private static Logger logger = LoggerFactory.getLogger(SendEmailToCompleteAccountDeletionProcess.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;

    public SendEmailToCompleteAccountDeletionProcess(IMailClient mailClient, IParentsService parentService,
    		ITokenGeneratorService tokenGeneratorService) {
        this.mailClient = mailClient;
        this.parentService = parentService;
    }

	@Override
	public void onApplicationEvent(AccountDeletionRequestEvent event) {
		Optional.ofNullable(parentService.getParentById(event.getIdentity()))
        .ifPresent(parent -> {
            logger.debug("Send Email To Complete Account Deletion Process");
            mailClient.sendMailForCompleteAccountDeletionProcess(parent.getEmail(), parent.getFirstName(), 
            		parent.getLastName(), event.getConfirmationToken(), new Locale(parent.getLocale()));
        });
	}

}
