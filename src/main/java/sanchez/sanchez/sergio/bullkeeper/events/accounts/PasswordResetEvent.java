package sanchez.sanchez.sergio.bullkeeper.events.accounts;

import org.springframework.context.ApplicationEvent;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.PasswordResetTokenDTO;

/**
 *
 * @author sergio
 */
public class PasswordResetEvent extends ApplicationEvent  {
	
	private static final long serialVersionUID = 1L;
	
	private final PasswordResetTokenDTO passwordResetToken;

	public PasswordResetEvent(Object source, PasswordResetTokenDTO passwordResetToken) {
		super(source);
		this.passwordResetToken = passwordResetToken;
	}

	public PasswordResetTokenDTO getPasswordResetToken() {
		return passwordResetToken;
	}
}
