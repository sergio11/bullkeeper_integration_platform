package sanchez.sanchez.sergio.bullkeeper.events.funtime;

import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DayScheduledDTO;

/**
 * Day Scheduled Saved Event
 * @author sergiosanchezsanchez
 *
 */
public final class DayScheduledSavedEvent extends ApplicationEvent {
	
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
	 * Day Scheduled
	 */
	private final DayScheduledDTO dayScheduled;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param dayScheduled
	 */
	public DayScheduledSavedEvent(final Object source, 
			final String kid, final String terminal, final DayScheduledDTO dayScheduled) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.dayScheduled = dayScheduled;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public DayScheduledDTO getDayScheduled() {
		return dayScheduled;
	}

	@Override
	public String toString() {
		return "DayScheduledSavedEvent [kid=" + kid + ", terminal=" + terminal + ", dayScheduled=" + dayScheduled + "]";
	}

	
	
}
