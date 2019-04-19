package sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group;

import javax.validation.GroupSequence;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IGroups.IAccountShouldActive;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IGroups.IAccountShouldNotLocked;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IGroups.IAccountShouldNotPendingDelete;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IGroups.IEmailShouldExist;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IGroups.IShouldNotBeAFacebookUser;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IGroups.IShouldNotBeAGoogleUser;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IGroups.IValidEmail;

@GroupSequence({ IValidEmail.class, IEmailShouldExist.class, IShouldNotBeAFacebookUser.class, IShouldNotBeAGoogleUser.class,
    IAccountShouldActive.class, IAccountShouldNotPendingDelete.class,  IAccountShouldNotLocked.class})
public interface IResetPasswordSequence {}
