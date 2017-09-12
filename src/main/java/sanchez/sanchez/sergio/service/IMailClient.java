package sanchez.sanchez.sergio.service;

/**
 *
 * @author sergio
 */
public interface IMailClient {
    
    void sendMailForActivateAccount(String email, String firstname, String lastname);
    void sendMailForResetPassword(String id, String email, String firstname, String lastname, String token);
    void sendMailForConfirmPasswordChange(String email, String firstname, String lastname);
    
}
