package sanchez.sanchez.sergio.bullkeeper.events.terminal;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class UnlinkTerminalEvent extends ApplicationEvent {
	

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
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 
	 */
	public UnlinkTerminalEvent(final Object source, final String kid, final String terminal) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	@Override
	public String toString() {
		return "UnlinkTerminalEvent [kid=" + kid + ", terminal=" + terminal + "]";
	}

}
