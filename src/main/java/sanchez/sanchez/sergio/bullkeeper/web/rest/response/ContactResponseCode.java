package sanchez.sanchez.sergio.bullkeeper.web.rest.response;
 
/**
 * Contact Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum ContactResponseCode implements IResponseCodeTypes{
	
    CONTACT_SAVED(1500L),
    CONTACTS_SAVED(1501L),
    SINGLE_CONTACT_DELETED(1502l),
    ALL_CONTACTS_DELETED(1503L),
    SINGLE_CONTACT_DETAIL(1504L),
    ALL_CONTACTS(1505L),
    CONTACT_NOT_FOUND(1506L),
    NO_CONTACTS_FOUND(1507L);
	
	private Long code;
	
	private ContactResponseCode(Long code) {
		this.code = code;
	}

	@Override
	public Long getResponseCode() {
	     return code;
	}
}
