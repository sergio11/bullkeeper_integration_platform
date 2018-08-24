package sanchez.sanchez.sergio.masoc.web.dto.request;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.masoc.persistence.constraints.ParentAccountShouldActive;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ParentAccountShouldNotLocked;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ParentEmailShouldExist;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ShouldNotBeAFacebookUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ShouldNotBeAGoogleUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IAccountShouldActive;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IAccountShouldNotLocked;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IEmailShouldExist;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IShouldNotBeAFacebookUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IShouldNotBeAGoogleUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IValidEmail;

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
