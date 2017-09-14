package es.bisite.usal.bullytect.service;

import java.util.Locale;

/**
 *
 * @author sergio
 */
public interface IMailClient {
    
    void sendMailForActivateAccount(String email, String firstname, String lastname, String confirmationToken, Locale locale);
    void sendMailForResetPassword(String id, String email, String firstname, String lastname, String token, Locale locale);
    void sendMailForConfirmPasswordChange(String email, String firstname, String lastname, Locale locale);
    void sendMailForConfirmAccountActivation(String email, String firstname, String lastname, Locale locale);
    void sendMailForConfirmRegistrationViaFacebook(String email, String firstname, String lastname, Locale locale);
    void sendMailForCompleteAccountDeletionProcess(String email, String firstname, String lastname, String confirmationToken, Locale locale);
    
}
