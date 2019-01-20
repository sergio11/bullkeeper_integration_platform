package sanchez.sanchez.sergio.bullkeeper.events.apps;

import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;

/**
 * New App Installed Event
 * @author sergiosanchezsanchez
 *
 */
public final class NewAppInstalledEvent extends ApplicationEvent {
	
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
	 * App
	 */
	private final AppInstalledDTO app;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param app
	 */
	public NewAppInstalledEvent(final Object source, final String kid, 
			final String terminal, final AppInstalledDTO app) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.app = app;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public AppInstalledDTO getApp() {
		return app;
	}

	@Override
	public String toString() {
		return "SaveFunTimeScheduledEvent [kid=" + kid + ", terminal=" + terminal + ", app=" + app + "]";
	}
}
