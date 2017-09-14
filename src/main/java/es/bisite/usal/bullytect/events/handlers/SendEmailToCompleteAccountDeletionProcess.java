package es.bisite.usal.bullytect.events.handlers;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import es.bisite.usal.bullytect.events.AccountDeletionRequestEvent;
import es.bisite.usal.bullytect.service.IMailClient;
import es.bisite.usal.bullytect.service.IParentsService;

@Component
public class SendEmailToCompleteAccountDeletionProcess  implements ApplicationListener<AccountDeletionRequestEvent>{
	
	private static Logger logger = LoggerFactory.getLogger(SendEmailToCompleteAccountDeletionProcess.class);
    
    private final IMailClient mailClient;
    private final IParentsService parentService;

    public SendEmailToCompleteAccountDeletionProcess(IMailClient mailClient, IParentsService parentService) {
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
