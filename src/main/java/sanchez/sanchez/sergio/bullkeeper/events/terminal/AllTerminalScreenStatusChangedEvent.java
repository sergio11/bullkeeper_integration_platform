package sanchez.sanchez.sergio.bullkeeper.events.terminal;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class AllTerminalScreenStatusChangedEvent extends ApplicationEvent {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Enabled
	 */
	private final Boolean enabled;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param enabled
	 */
	public AllTerminalScreenStatusChangedEvent(Object source, String kid, Boolean enabled) {
		super(source);
		this.kid = kid;
		this.enabled = enabled;
	}

	public String getKid() {
		return kid;
	}


	public Boolean getEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "AllTerminalScreenStatusChangedEvent [kid=" + kid + ", enabled=" + enabled + "]";
	}

	
}
