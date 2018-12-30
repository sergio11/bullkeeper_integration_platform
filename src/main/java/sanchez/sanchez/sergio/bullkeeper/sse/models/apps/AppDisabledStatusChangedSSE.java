package sanchez.sanchez.sergio.bullkeeper.sse.models.apps;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * App Disabled Status Changed
 * @author sergiosanchezsanchez
 *
 */
public final class AppDisabledStatusChangedSSE
	extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "APP_DISABLED_STATUS_CHANGED";
	
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
	 * Disabled
	 */
	@JsonProperty("disabled")
	private Boolean disabled;
	
	/**
	 * 
	 */
	public AppDisabledStatusChangedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 * @param app
	 * @param disabled
	 */
	public AppDisabledStatusChangedSSE(String subscriberId, String kid, String terminal, String app, Boolean disabled) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
		this.app = app;
		this.disabled = disabled;
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

	public Boolean getDisabled() {
		return disabled;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		return "AppDisabledStatusChangedSSE [kid=" + kid + ", terminal=" + terminal + ", app=" + app + ", disabled="
				+ disabled + "]";
	}
	
}
