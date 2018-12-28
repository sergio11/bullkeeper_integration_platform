package sanchez.sanchez.sergio.bullkeeper.events.apps;

import org.springframework.context.ApplicationEvent;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppRulesDTO;

/**
 * App Rules List Saved Event
 * @author sergiosanchezsanchez
 *
 */
public final class AppRulesListSavedEvent extends ApplicationEvent {
	
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
	 * Save App Rule
	 */
	private final Iterable<SaveAppRulesDTO> saveAppRulesDTOList;

	
	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param saveAppRulesDTOList
	 */
	public AppRulesListSavedEvent(Object source, String kid, 
			String terminal, Iterable<SaveAppRulesDTO> saveAppRulesDTOList) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.saveAppRulesDTOList = saveAppRulesDTOList;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public Iterable<SaveAppRulesDTO> getSaveAppRulesDTO() {
		return saveAppRulesDTOList;
	}


}
