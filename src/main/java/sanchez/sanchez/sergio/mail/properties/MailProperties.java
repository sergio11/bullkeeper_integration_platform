package sanchez.sanchez.sergio.mail.properties;

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
	
}
