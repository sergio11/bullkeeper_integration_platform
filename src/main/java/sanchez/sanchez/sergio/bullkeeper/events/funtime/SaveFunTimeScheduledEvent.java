package sanchez.sanchez.sergio.bullkeeper.events.funtime;


import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.FunTimeScheduledDTO;
/**
 * Save Fun Time Scheduled Event
 * @author sergiosanchezsanchez
 *
 */
public final class SaveFunTimeScheduledEvent extends ApplicationEvent {
	
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
	 * Fun Time
	 */
	private final FunTimeScheduledDTO funTime;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param funTime
	 */
	public SaveFunTimeScheduledEvent(final Object source, 
			final String kid, final String terminal, final FunTimeScheduledDTO funTime) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.funTime = funTime;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public FunTimeScheduledDTO getFunTime() {
		return funTime;
	}

	@Override
	public String toString() {
		return "SaveFunTimeScheduledEvent [kid=" + kid + ", terminal=" + terminal + ", funTime=" + funTime + "]";
	}
	
}
