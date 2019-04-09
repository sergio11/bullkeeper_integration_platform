package sanchez.sanchez.sergio.bullkeeper.events.terminal;

import org.springframework.context.ApplicationEvent;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalStatusEnum;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class TerminalStatusChangedEvent extends ApplicationEvent {
	

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
	 * Status
	 */
	private final TerminalStatusEnum status;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param status
	 */
	public TerminalStatusChangedEvent(Object source, String kid, String terminal, TerminalStatusEnum status) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.status = status;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public TerminalStatusEnum getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "TerminalStatusChangedEvent [kid=" + kid + ", terminal=" + terminal + ", status=" + status + "]";
	}
}
