package sanchez.sanchez.sergio.bullkeeper.events.apps;

import org.springframework.context.ApplicationEvent;

/**
 * App Disabled Event
 * @author sergiosanchezsanchez
 *
 */
public final class AppDisabledEvent extends ApplicationEvent {
	
	
	
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
	private final String app;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param app
	 */
	public AppDisabledEvent(Object source, String kid, String terminal, String app) {
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

	public String getApp() {
		return app;
	}

	@Override
	public String toString() {
		return "AppDisabledEvent [kid=" + kid + ", terminal=" + terminal + ", app=" + app + "]";
	}


	

}
