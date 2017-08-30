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
    
    @Value("${mail.from}")
    private String mailFrom;
    
    @Value("${mail.content.registration.success.template.name}")
    private String registrationSuccessTemplate;

    public String getMailFrom() {
        return mailFrom;
    }
   
    public String getRegistrationSuccessTemplate() {
        return registrationSuccessTemplate;
    }
    
}
