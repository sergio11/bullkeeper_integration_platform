package sanchez.sanchez.sergio.bullkeeper.events.accounts.handler;

import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IGuardianService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITokenGeneratorService;
import sanchez.sanchez.sergio.bullkeeper.events.accounts.AccountDeletionRequestEvent;
import sanchez.sanchez.sergio.bullkeeper.events.accounts.EmailChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.accounts.ParentAccountActivatedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.accounts.ParentRegistrationByFacebookSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.accounts.ParentRegistrationByGoogleSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.accounts.ParentRegistrationSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.accounts.PasswordChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.accounts.PasswordResetEvent;
import sanchez.sanchez.sergio.bullkeeper.mail.service.IMailClientService;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Component
public class AccountsEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(AccountsEventHandlers.class);
	    
	private final IMailClientService mailClient;
	private final IGuardianService guardianService;
	private final ITokenGeneratorService tokenGeneratorService;
	
	
	public AccountsEventHandlers(IMailClientService mailClient, IGuardianService guardianService,
			ITokenGeneratorService tokenGeneratorService) {
		super();
		this.mailClient = mailClient;
		this.guardianService = guardianService;
		this.tokenGeneratorService = tokenGeneratorService;
	}

	/**
	 * Send Email To Complete Account Deletion Process
	 * @param accountDeletionRequestEvent
	 */
	@EventListener
    void handle(final AccountDeletionRequestEvent accountDeletionRequestEvent) {
		Optional.ofNullable(guardianService.getGuardianById(accountDeletionRequestEvent.getIdentity()))
	        .ifPresent(guardian -> {
	            logger.debug("Send Email To Complete Account Deletion Process");
	            mailClient.sendMailForCompleteAccountDeletionProcess(guardian.getEmail(), guardian.getFirstName(), 
	            		guardian.getLastName(), accountDeletionRequestEvent.getConfirmationToken(), new Locale(guardian.getLocale()));
	        });
    }
	
	
	/**
	 * Send email to confirm registration via facebook
	 * @param parentRegistrationByFacebookSuccessEvent
	 */
	@EventListener
	void handle(final ParentRegistrationByFacebookSuccessEvent parentRegistrationByFacebookSuccessEvent) {
		Optional.ofNullable(guardianService.getGuardianById(parentRegistrationByFacebookSuccessEvent.getIdentity()))
	        .ifPresent(guardian -> {
	            logger.debug("Send Email To Confirm Registration via Facebook");
	            mailClient.sendMailForConfirmRegistrationViaFacebook(guardian.getEmail(), guardian.getFirstName(), 
	            		guardian.getLastName(), new Locale(guardian.getLocale()));
	        });
	}
	
	/**
	 * Send email to confirm registration via google
	 * @param parentRegistrationByGoogleSuccessEvent
	 */
	@EventListener
	void handle(final ParentRegistrationByGoogleSuccessEvent parentRegistrationByGoogleSuccessEvent) {
		Optional.ofNullable(guardianService.getGuardianById(parentRegistrationByGoogleSuccessEvent.getIdentity()))
	        .ifPresent(guardian -> {
	            logger.debug("Send Email To Confirm Registration via Google");
	            mailClient.sendMailForConfirmRegistrationViaGoogle(guardian.getEmail(), guardian.getFirstName(), 
	            		guardian.getLastName(), new Locale(guardian.getLocale()));
	        });
	}
	
	/**
	 * Send Mail For Activate Account
	 * @param parentRegistrationSuccessEvent
	 */
	@EventListener
	void handle(final ParentRegistrationSuccessEvent parentRegistrationSuccessEvent) {
		logger.debug("Handle Event: ParentRegistrationSuccessEvent ");
    	
            Optional.ofNullable(guardianService.getGuardianById(parentRegistrationSuccessEvent.getIdentity()))
                .ifPresent(guardian -> {
                    logger.debug("Save Confirmation token for user");
                    String confirmationToken = tokenGeneratorService.generateToken(guardian.getFirstName());
                    // Set Account as inactive and save activation token
                    guardianService.setAsNotActiveAndConfirmationToken(guardian.getIdentity(), confirmationToken);
                    // Send Mail for Activate Account
                    logger.debug("Send email to: " + guardian.getEmail());
                    mailClient.sendMailForActivateAccount(guardian.getEmail(), guardian.getFirstName(), 
                    		guardian.getLastName(), confirmationToken, new Locale(guardian.getLocale()));
                });
	}
	
	
	/**
	 * Send Mail To Confirm Account Activation
	 * @param parentAccountActivatedEvent
	 */
	@EventListener
	void handle(final ParentAccountActivatedEvent parentAccountActivatedEvent) {
		Optional.ofNullable(guardianService.getGuardianById(parentAccountActivatedEvent.getIdentity()))
        .ifPresent(guardian -> {
            logger.debug("Send Email To Confirm Account Activation");
            mailClient.sendMailForConfirmAccountActivation(guardian.getEmail(), guardian.getFirstName(), 
            		guardian.getLastName(), new Locale(guardian.getLocale()));
        });
	}
	
	/**
	 * Send Mail To Confirm Password Change
	 * @param passwordChangedEvent
	 */
	@EventListener
	void handle(final PasswordChangedEvent passwordChangedEvent) {
		Optional.ofNullable(guardianService.getGuardianById(passwordChangedEvent.getParentId()))
	        .ifPresent(guardian -> {
	            logger.debug("Send Email To Confirm Password Change");
	            mailClient.sendMailForConfirmPasswordChange(guardian.getEmail(), guardian.getFirstName(), 
	            		guardian.getLastName(), new Locale(guardian.getLocale()));
	        });
	}
	
	/**
	 * Send Password Reset Token
	 * @param passwordResetEvent
	 */
	@EventListener
	void handle(final PasswordResetEvent passwordResetEvent) {
		Optional.ofNullable(guardianService.getGuardianById(passwordResetEvent.getPasswordResetToken().getUser()))
        .ifPresent(guardian -> {
            logger.debug("Send mail for reset password token");
            // Send Mail for Activate Account
            logger.debug("Send email to: " + guardian.getEmail());
            mailClient.sendMailForResetPassword(guardian.getIdentity(), guardian.getEmail(), guardian.getFirstName(), 
            		guardian.getLastName(), passwordResetEvent.getPasswordResetToken().getToken(), guardian.getLocale() != null ? new Locale(guardian.getLocale()): Locale.getDefault());
        });
	}
	
	/**
	 * Email Changed Event
	 * @param emailChangedEvent
	 */
	@EventListener
	void handle(final EmailChangedEvent emailChangedEvent) {
		Optional.ofNullable(guardianService.getGuardianByEmail(emailChangedEvent.getNewEmail()))
        .ifPresent(guardian -> {
            logger.debug("Send mail for reset password token");
            logger.debug("Send mail for reset password token");
            // Send Mail for Activate Account
            logger.debug("Send email to: " + guardian.getEmail());
            mailClient.sendMailForEmailChanged(guardian.getIdentity(), guardian.getEmail(), guardian.getFirstName(), 
            		guardian.getLastName(), emailChangedEvent.getNewEmail(), guardian.getLocale() != null ? new Locale(guardian.getLocale()): Locale.getDefault());
        });
	}
}
