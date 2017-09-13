package sanchez.sanchez.sergio.service;

/**
 *
 * @author sergio
 */
public interface IMailContentBuilderService {
    String buildRegistrationSuccessTemplate(String firstname, String lastname, String confirmationToken);
    String buildPasswordResetTemplate(String id, String firstname, String lastname, String token);
    String buildConfirmPasswordChangeTemplate(String firstname, String lastname);
    String buildConfirmAccountActivationTemplate(String firstname, String lastname);
    String buildConfirmRegistrationViaFacebookTemplate(String firstname, String lastname);
}
