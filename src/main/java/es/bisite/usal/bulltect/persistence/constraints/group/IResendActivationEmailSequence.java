package es.bisite.usal.bulltect.persistence.constraints.group;

import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IAccountShouldNotActive;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IAccountShouldNotLocked;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IEmailShouldExist;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IShouldNotBeAFacebookUser;
import es.bisite.usal.bulltect.persistence.constraints.group.IGroups.IValidEmail;
import javax.validation.GroupSequence;

@GroupSequence({ IValidEmail.class, IEmailShouldExist.class, 
    IAccountShouldNotActive.class, IShouldNotBeAFacebookUser.class, IAccountShouldNotLocked.class})
public interface IResendActivationEmailSequence {}
