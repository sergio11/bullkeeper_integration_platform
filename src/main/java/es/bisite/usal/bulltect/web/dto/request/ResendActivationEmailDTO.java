
package es.bisite.usal.bulltect.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.bisite.usal.bulltect.persistence.constraints.ParentAccountShouldNotActive;
import es.bisite.usal.bulltect.persistence.constraints.ParentAccountShouldNotLocked;
import es.bisite.usal.bulltect.persistence.constraints.ParentEmailShouldExist;
import es.bisite.usal.bulltect.persistence.constraints.ShouldNotBeAFacebookUser;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IAccountShouldNotActive;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IAccountShouldNotLocked;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IEmailShouldExist;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IShouldNotBeAFacebookUser;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IValidEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author sergio
 */
public class ResendActivationEmailDTO {
    
    @NotBlank(message = "{user.email.notnull}")
    @Email(message = "{user.email.invalid}", groups = IValidEmail.class)
    @ParentEmailShouldExist(message = "{user.email.not.exists}", groups = IEmailShouldExist.class)
    @ParentAccountShouldNotActive(message = "{user.email.should.not.activate}", groups = IAccountShouldNotActive.class)
    @ShouldNotBeAFacebookUser(message = "{user.should.not.be.a.facebook.user}", groups = IShouldNotBeAFacebookUser.class)
    @ParentAccountShouldNotLocked(message = "{user.email.not.locked}", groups = IAccountShouldNotLocked.class)
    @JsonProperty("email")
    private String email;
    
    public ResendActivationEmailDTO(){}

    public ResendActivationEmailDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
