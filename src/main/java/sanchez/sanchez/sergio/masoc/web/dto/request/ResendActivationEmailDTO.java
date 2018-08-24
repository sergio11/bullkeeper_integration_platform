
package sanchez.sanchez.sergio.masoc.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.masoc.persistence.constraints.ParentAccountShouldNotActive;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ParentAccountShouldNotLocked;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ParentEmailShouldExist;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ShouldNotBeAFacebookUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ShouldNotBeAGoogleUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IAccountShouldNotActive;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IAccountShouldNotLocked;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IEmailShouldExist;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IShouldNotBeAFacebookUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IShouldNotBeAGoogleUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IValidEmail;

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
    @ShouldNotBeAGoogleUser(message = "{user.should.not.be.a.google.user}", groups = IShouldNotBeAGoogleUser.class)
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
