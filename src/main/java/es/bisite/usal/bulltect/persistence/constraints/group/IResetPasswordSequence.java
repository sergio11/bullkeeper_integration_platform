package es.bisite.usal.bulltect.persistence.constraints.group;

import javax.validation.GroupSequence;
import es.bisite.usal.bulltect.persistence.constraints.group.IResetPasswordSequence.IValidEmail;
import es.bisite.usal.bulltect.persistence.constraints.group.IResetPasswordSequence.IEmailShouldExist;
import es.bisite.usal.bulltect.persistence.constraints.group.IResetPasswordSequence.IAccountShouldActive;
import es.bisite.usal.bulltect.persistence.constraints.group.IResetPasswordSequence.IAccountShouldNotLocked;

@GroupSequence({ IValidEmail.class, IEmailShouldExist.class, IAccountShouldActive.class, IAccountShouldNotLocked.class})
public interface IResetPasswordSequence {

	public interface IValidEmail {}
	public interface IEmailShouldExist {}
	public interface IAccountShouldActive {}
	public interface IAccountShouldNotLocked {}
}
