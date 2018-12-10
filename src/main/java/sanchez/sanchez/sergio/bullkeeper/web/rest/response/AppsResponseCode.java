package sanchez.sanchez.sergio.bullkeeper.web.rest.response;
 
/**
 * Apps Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum AppsResponseCode implements IResponseCodeTypes{
	
	ALL_APPS_INSTALLED_IN_THE_TERMINAL(1200L), 
    NO_APPS_INSTALLED_FOUND(1201L),
    APPS_INSTALLED_SAVED(1202L), 
    ALL_APPS_INSTALLED_DELETED(1203L), 
    APP_INSTALLED_DELETED(1204L), 
    APP_INSTALLED_NOT_FOUND(1205L),
    NEW_APP_INSTALLED_ADDED(1206L), 
    APP_RULES_WERE_APPLIED(1207L),
    APP_INSTALLED_DETAIL(1208L);
	
	private Long code;
	
	private AppsResponseCode(Long code) {
		this.code = code;
	}

	@Override
	public Long getResponseCode() {
	     return code;
	}
}
