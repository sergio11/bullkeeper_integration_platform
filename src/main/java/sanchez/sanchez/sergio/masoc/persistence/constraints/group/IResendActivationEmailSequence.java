package sanchez.sanchez.sergio.masoc.persistence.constraints.group;

import javax.validation.GroupSequence;

import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IAccountShouldNotActive;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IAccountShouldNotLocked;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IEmailShouldExist;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IShouldNotBeAFacebookUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IShouldNotBeAGoogleUser;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.IGroups.IValidEmail;

@GroupSequence({ IValidEmail.class, IEmailShouldExist.class, 
    IAccountShouldNotActive.class, IShouldNotBeAFacebookUser.class, IShouldNotBeAGoogleUser.class, IAccountShouldNotLocked.class})
public interface IResendActivationEmailSequence {}
