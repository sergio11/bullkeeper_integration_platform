package sanchez.sanchez.sergio.bullkeeper.events.terminal;

import org.springframework.context.ApplicationEvent;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDTO;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class UnlinkAllKidTerminalsEvent extends ApplicationEvent {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Terminals
	 */
	private final Iterable<TerminalDTO> terminals;
	
	
	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminals
	 */
	public UnlinkAllKidTerminalsEvent(final Object source, final String kid,
			final Iterable<TerminalDTO> terminals) {
		super(source);
		this.kid = kid;
		this.terminals = terminals;
	}

	public String getKid() {
		return kid;
	}
	
	

	public Iterable<TerminalDTO> getTerminals() {
		return terminals;
	}

	@Override
	public String toString() {
		return "UnlinkAllKidTerminalsEvent [kid=" + kid + ", terminals=" + terminals + "]";
	}

	
}
