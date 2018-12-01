package sanchez.sanchez.sergio.bullkeeper.events.handlers;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IGuardianService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITokenGeneratorService;
import sanchez.sanchez.sergio.bullkeeper.events.AccountDeletionRequestEvent;
import sanchez.sanchez.sergio.bullkeeper.events.ParentAccountActivatedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.ParentRegistrationByFacebookSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.ParentRegistrationByGoogleSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.ParentRegistrationSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.PasswordChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.PasswordResetEvent;
import sanchez.sanchez.sergio.bullkeeper.mail.service.IMailClientService;

@Component
public class AccountsEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(AccountsEventHandlers.class);
	    
	private final IMailClientService mailClient;
	private final IGuardianService parentService;
	private final ITokenGeneratorService tokenGeneratorService;
	
	
	public AccountsEventHandlers(IMailClientService mailClient, IGuardianService parentService,
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
		Optional.ofNullable(parentService.getGuardianById(accountDeletionRequestEvent.getIdentity()))
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
		Optional.ofNullable(parentService.getGuardianById(parentRegistrationByFacebookSuccessEvent.getIdentity()))
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
		Optional.ofNullable(parentService.getGuardianById(parentRegistrationByGoogleSuccessEvent.getIdentity()))
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
    	
            Optional.ofNullable(parentService.getGuardianById(parentRegistrationSuccessEvent.getIdentity()))
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
		Optional.ofNullable(parentService.getGuardianById(parentAccountActivatedEvent.getIdentity()))
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
		Optional.ofNullable(parentService.getGuardianById(passwordChangedEvent.getParentId()))
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
		Optional.ofNullable(parentService.getGuardianById(passwordResetEvent.getPasswordResetToken().getUser()))
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
