package es.bisite.usal.bulltect.mail.service;

import java.util.Locale;

import es.bisite.usal.bulltect.persistence.entity.EmailTypeEnum;

/**
 *
 * @author sergio
 */
public interface IMailClientService {
    void sendEmail(String email, EmailTypeEnum type, String subject, String content);
    void sendMailForActivateAccount(String email, String firstname, String lastname, String confirmationToken, Locale locale);
    void sendMailForResetPassword(String id, String email, String firstname, String lastname, String token, Locale locale);
    void sendMailForConfirmPasswordChange(String email, String firstname, String lastname, Locale locale);
    void sendMailForConfirmAccountActivation(String email, String firstname, String lastname, Locale locale);
    void sendMailForConfirmRegistrationViaFacebook(String email, String firstname, String lastname, Locale locale);
    void sendMailForConfirmRegistrationViaGoogle(String email, String firstname, String lastname, Locale locale);
    void sendMailForCompleteAccountDeletionProcess(String email, String firstname, String lastname, String confirmationToken, Locale locale);
}
