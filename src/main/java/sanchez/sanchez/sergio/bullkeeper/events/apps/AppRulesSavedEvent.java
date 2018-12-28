package sanchez.sanchez.sergio.bullkeeper.events.apps;

import org.springframework.context.ApplicationEvent;

/**
 * App Rules Saved Event
 * @author sergiosanchezsanchez
 *
 */
public final class AppRulesSavedEvent extends ApplicationEvent {
	
	
	
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
	 * App Rule
	 */
	private final String appRule;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param app
	 * @param appRule
	 */
	public AppRulesSavedEvent(Object source, String kid, String terminal, String app, String appRule) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.app = app;
		this.appRule = appRule;
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

	public String getAppRule() {
		return appRule;
	}

	@Override
	public String toString() {
		return "AppRulesSavedEvent [kid=" + kid + ", terminal=" + terminal + ", app=" + app + ", appRule=" + appRule
				+ "]";
	}

}
