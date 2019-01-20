package sanchez.sanchez.sergio.bullkeeper.events.apps;

import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;

/**
 * Uninstall App Event
 * @author sergiosanchezsanchez
 *
 */
public final class UninstallAppEvent extends ApplicationEvent {
	
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
	public UninstallAppEvent(Object source, String kid, String terminal, AppInstalledDTO app) {
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
		return "UninstallAppEvent [kid=" + kid + ", terminal=" + terminal + ", app=" + app + "]";
	}
}
