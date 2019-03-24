package sanchez.sanchez.sergio.bullkeeper.events.terminal;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class TerminalPhoneCallsStatusChangedEvent extends ApplicationEvent {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Terminal
	 */
	private final String terminal;
	
	/**
	 * Enabled
	 */
	private final Boolean enabled;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param enabled
	 */
	public TerminalPhoneCallsStatusChangedEvent(Object source, String kid, 
			String terminal, Boolean enabled) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.enabled = enabled;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "TerminalPhoneCallsStatusChangedEvent [kid=" + kid + ", terminal=" + terminal + ", enabled=" + enabled
				+ "]";
	}

}
