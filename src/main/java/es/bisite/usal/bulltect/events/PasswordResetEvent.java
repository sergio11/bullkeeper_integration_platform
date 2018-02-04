package es.bisite.usal.bulltect.events;

import org.springframework.context.ApplicationEvent;

import es.bisite.usal.bulltect.web.dto.response.PasswordResetTokenDTO;

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
