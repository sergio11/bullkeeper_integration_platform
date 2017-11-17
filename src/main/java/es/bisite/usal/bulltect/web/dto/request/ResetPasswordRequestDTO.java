package es.bisite.usal.bulltect.web.dto.request;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.bisite.usal.bulltect.persistence.constraints.ParentAccountShouldActive;
import es.bisite.usal.bulltect.persistence.constraints.ParentAccountShouldNotLocked;
import es.bisite.usal.bulltect.persistence.constraints.ParentEmailShouldExist;
import es.bisite.usal.bulltect.persistence.constraints.ShouldNotBeAFacebookUser;
import es.bisite.usal.bulltect.persistence.constraints.ShouldNotBeAGoogleUser;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IValidEmail;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IAccountShouldActive;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IAccountShouldNotLocked;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IEmailShouldExist;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IShouldNotBeAFacebookUser;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IShouldNotBeAGoogleUser;

public final class ResetPasswordRequestDTO {

    @NotBlank(message = "{user.email.notnull}")
    @Email(message = "{user.email.invalid}", groups = IValidEmail.class)
    @ParentEmailShouldExist(message = "{user.email.not.exists}", groups = IEmailShouldExist.class)
    @ShouldNotBeAFacebookUser(message = "{user.should.not.be.a.facebook.user}", groups = IShouldNotBeAFacebookUser.class)
    @ShouldNotBeAGoogleUser(message = "{user.should.not.be.a.google.user}", groups = IShouldNotBeAGoogleUser.class)
    @ParentAccountShouldActive(message = "{user.email.not.activate}", groups = IAccountShouldActive.class)
    @ParentAccountShouldNotLocked(message = "{user.email.not.locked}", groups = IAccountShouldNotLocked.class)
    @JsonProperty("email")
    private String email;

    public ResetPasswordRequestDTO() {
    }

    public ResetPasswordRequestDTO(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
