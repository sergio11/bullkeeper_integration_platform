package sanchez.sanchez.sergio.bullkeeper.sse.models.apps;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * App Rules List Saved SSE
 * @author sergiosanchezsanchez
 *
 */
public final class AppRulesListSavedSSE
	extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "APP_RULES_LIST_SAVED_EVENT";
	
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
	 * App Rules List
	 */
	@JsonProperty("app_rules")
	private Iterable<AppRules> appRulesList;
	
	/**
	 * 
	 */
	public AppRulesListSavedSSE(){
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 * @param appRulesList
	 */
	public AppRulesListSavedSSE(String subscriberId, String kid, String terminal, Iterable<AppRules> appRulesList) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
		this.appRulesList = appRulesList;
	}


	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public Iterable<AppRules> getAppRulesList() {
		return appRulesList;
	}

	/**
	 * App Rules
	 * @author sergiosanchezsanchez
	 *
	 */
	public static class AppRules {
		
		/**
		 * Identity
		 */
		@JsonProperty("identity")
		private final String identity;
		
		/**
		 * Type
		 */
		@JsonProperty("type")
		private final String type;
		
		
		/**
		 * 
		 * @param identity
		 * @param type
		 */
		public AppRules(String identity, String type) {
			super();
			this.identity = identity;
			this.type = type;
		}

		public String getIdentity() {
			return identity;
		}

		public String getType() {
			return type;
		}

		@Override
		public String toString() {
			return "AppRules [identity=" + identity + ", type=" + type + "]";
		}
		
		
		
	}

}
