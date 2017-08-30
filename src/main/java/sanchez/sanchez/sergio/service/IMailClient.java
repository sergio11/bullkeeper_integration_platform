package sanchez.sanchez.sergio.service;

/**
 *
 * @author sergio
 */
public interface IMailClient {
    
    void sendMailForActivateAccount(String email, String firstname, String lastname);
    
}
