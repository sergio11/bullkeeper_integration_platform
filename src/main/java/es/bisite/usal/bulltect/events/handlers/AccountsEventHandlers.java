package es.bisite.usal.bulltect.events.handlers;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.domain.service.IParentsService;
import es.bisite.usal.bulltect.domain.service.ITokenGeneratorService;
import es.bisite.usal.bulltect.events.AccountDeletionRequestEvent;
import es.bisite.usal.bulltect.events.ParentAccountActivatedEvent;
import es.bisite.usal.bulltect.events.ParentRegistrationByFacebookSuccessEvent;
import es.bisite.usal.bulltect.events.ParentRegistrationByGoogleSuccessEvent;
import es.bisite.usal.bulltect.events.ParentRegistrationSuccessEvent;
import es.bisite.usal.bulltect.events.PasswordChangedEvent;
import es.bisite.usal.bulltect.events.PasswordResetEvent;
import es.bisite.usal.bulltect.mail.service.IMailClientService;

@Component
public class AccountsEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(AccountsEventHandlers.class);
	    
	private final IMailClientService mailClient;
	private final IParentsService parentService;
	private final ITokenGeneratorService tokenGeneratorService;
	
	
	public AccountsEventHandlers(IMailClientService mailClient, IParentsService parentService,
			ITokenGeneratorService tokenGeneratorService) {
		super();
		this.mailClient = mailClient;
		this.parentService = parentService;
		this.tokenGeneratorService = tokenGeneratorService;
	}

	/**
	 * Send Email To Complete Account Deletion Process
	 * @param accountDeletionRequestEvent
	 */
	@EventListener
    void handle(final AccountDeletionRequestEvent accountDeletionRequestEvent) {
		Optional.ofNullable(parentService.getParentById(accountDeletionRequestEvent.getIdentity()))
	        .ifPresent(parent -> {
	            logger.debug("Send Email To Complete Account Deletion Process");
	            mailClient.sendMailForCompleteAccountDeletionProcess(parent.getEmail(), parent.getFirstName(), 
	            		parent.getLastName(), accountDeletionRequestEvent.getConfirmationToken(), new Locale(parent.getLocale()));
	        });
    }
	
	
	/**
	 * Send email to confirm registration via facebook
	 * @param parentRegistrationByFacebookSuccessEvent
	 */
	@EventListener
	void handle(final ParentRegistrationByFacebookSuccessEvent parentRegistrationByFacebookSuccessEvent) {
		Optional.ofNullable(parentService.getParentById(parentRegistrationByFacebookSuccessEvent.getIdentity()))
	        .ifPresent(parent -> {
	            logger.debug("Send Email To Confirm Registration via Facebook");
	            mailClient.sendMailForConfirmRegistrationViaFacebook(parent.getEmail(), parent.getFirstName(), 
	            		parent.getLastName(), new Locale(parent.getLocale()));
	        });
	}
	
	/**
	 * Send email to confirm registration via google
	 * @param parentRegistrationByGoogleSuccessEvent
	 */
	@EventListener
	void handle(final ParentRegistrationByGoogleSuccessEvent parentRegistrationByGoogleSuccessEvent) {
		Optional.ofNullable(parentService.getParentById(parentRegistrationByGoogleSuccessEvent.getIdentity()))
	        .ifPresent(parent -> {
	            logger.debug("Send Email To Confirm Registration via Google");
	            mailClient.sendMailForConfirmRegistrationViaGoogle(parent.getEmail(), parent.getFirstName(), 
	            		parent.getLastName(), new Locale(parent.getLocale()));
	        });
	}
	
	/**
	 * Send Mail For Activate Account
	 * @param parentRegistrationSuccessEvent
	 */
	@EventListener
	void handle(final ParentRegistrationSuccessEvent parentRegistrationSuccessEvent) {
		logger.debug("Handle Event: ParentRegistrationSuccessEvent ");
    	
            Optional.ofNullable(parentService.getParentById(parentRegistrationSuccessEvent.getIdentity()))
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
	
	
	/**
	 * Send Mail To Confirm Account Activation
	 * @param parentAccountActivatedEvent
	 */
	@EventListener
	void handle(final ParentAccountActivatedEvent parentAccountActivatedEvent) {
		Optional.ofNullable(parentService.getParentById(parentAccountActivatedEvent.getIdentity()))
        .ifPresent(parent -> {
            logger.debug("Send Email To Confirm Account Activation");
            mailClient.sendMailForConfirmAccountActivation(parent.getEmail(), parent.getFirstName(), 
            		parent.getLastName(), new Locale(parent.getLocale()));
        });
	}
	
	/**
	 * Send Mail To Confirm Password Change
	 * @param passwordChangedEvent
	 */
	@EventListener
	void handle(final PasswordChangedEvent passwordChangedEvent) {
		Optional.ofNullable(parentService.getParentById(passwordChangedEvent.getParentId()))
	        .ifPresent(parent -> {
	            logger.debug("Send Email To Confirm Password Change");
	            mailClient.sendMailForConfirmPasswordChange(parent.getEmail(), parent.getFirstName(), 
	            		parent.getLastName(), new Locale(parent.getLocale()));
	        });
	}
	
	/**
	 * Send Password Reset Token
	 * @param passwordResetEvent
	 */
	@EventListener
	void handle(final PasswordResetEvent passwordResetEvent) {
		Optional.ofNullable(parentService.getParentById(passwordResetEvent.getPasswordResetToken().getUser()))
        .ifPresent(parent -> {
            logger.debug("Send mail for reset password token");
            logger.debug("Send mail for reset password token");
            // Send Mail for Activate Account
            logger.debug("Send email to: " + parent.getEmail());
            mailClient.sendMailForResetPassword(parent.getIdentity(), parent.getEmail(), parent.getFirstName(), 
            		parent.getLastName(), passwordResetEvent.getPasswordResetToken().getToken(), parent.getLocale() != null ? new Locale(parent.getLocale()): Locale.getDefault());
        });
	}
}
