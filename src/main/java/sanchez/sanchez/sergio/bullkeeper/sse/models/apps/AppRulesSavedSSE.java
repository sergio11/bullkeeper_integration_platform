package sanchez.sanchez.sergio.bullkeeper.sse.models.apps;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * App Rules Saved SSE
 * @author sergiosanchezsanchez
 *
 */
public final class AppRulesSavedSSE
	extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "APP_RULES_SAVED_EVENT";
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;
	
	/**
	 * App
	 */
	@JsonProperty("app")
	private String app;
	
	
	/**
	 * Type
	 */
	@JsonProperty("type")
	private String type;
	
	/**
	 * 
	 */
	public AppRulesSavedSSE() {
		this.eventType = EVENT_TYPE;
	}

	

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 * @param app
	 * @param type
	 */
	public AppRulesSavedSSE(String subscriberId, String kid, String terminal, String app, String type) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
		this.app = app;
		this.type = type;
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


	public String getType() {
		return type;
	}


	@Override
	public String toString() {
		return "AppRulesSavedSSE [kid=" + kid + ", terminal=" + terminal + ", app=" + app + ", type=" + type + "]";
	}
}
