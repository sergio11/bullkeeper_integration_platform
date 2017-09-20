package es.bisite.usal.bulltect.mail.service;

import java.util.Locale;

/**
 *
 * @author sergio
 */
public interface IMailContentBuilderService {
    String buildRegistrationSuccessTemplate(String firstname, String lastname, String confirmationToken, Locale locale);
    String buildPasswordResetTemplate(String id, String firstname, String lastname, String token, Locale locale);
    String buildConfirmPasswordChangeTemplate(String firstname, String lastname, Locale locale);
    String buildConfirmAccountActivationTemplate(String firstname, String lastname, Locale locale);
    String buildConfirmRegistrationViaFacebookTemplate(String firstname, String lastname, Locale locale);
    String buildCompleteAccountDeletionProcessTemplate(String firstname, String lastname, String confirmationToken, Locale locale);
}
