package es.bisite.usal.bulltect.mail.properties;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author sergio
 */
@Component
public class MailProperties implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Value("${mail.from}")
    private String mailFrom;
    
    @Value("${mail.registration.success.template.name}")
    private String registrationSuccessTemplate;
    
    @Value("${mail.password.reset.template.name}")
    private String passWordResetTemplate;
    
    @Value("${mail.confirm.password.change.template.name}")
    private String confirmPasswordChangeTemplate;
    
    @Value("${mail.confirm.account.activation.template.name}")
    private String confirmAccountActivationTemplate;
    
    @Value("${mail.confirm.registration.via.facebook.template.name}")
    private String confirmRegistrationViaFacebookTemplate;
    
    @Value("${mail.confirm.registration.via.google.template.name}")
    private String confirmRegistrationViaGoogleTemplate;
    
    @Value("${mail.complete.account.deletion.process.template.name}")
    private String completeAccountDeletionProcessTemplate;
    
    public String getMailFrom() {
        return mailFrom;
    }
   
    public String getRegistrationSuccessTemplate() {
        return registrationSuccessTemplate;
    }

	public String getPassWordResetTemplate() {
		return passWordResetTemplate;
	}

	public String getConfirmPasswordChangeTemplate() {
		return confirmPasswordChangeTemplate;
	}

	public String getConfirmAccountActivationTemplate() {
		return confirmAccountActivationTemplate;
	}

	public String getConfirmRegistrationViaFacebookTemplate() {
		return confirmRegistrationViaFacebookTemplate;
	}
	

	public String getConfirmRegistrationViaGoogleTemplate() {
		return confirmRegistrationViaGoogleTemplate;
	}

	public String getCompleteAccountDeletionProcessTemplate() {
		return completeAccountDeletionProcessTemplate;
	}
	
	
}
