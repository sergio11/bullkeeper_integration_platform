package sanchez.sanchez.sergio.bullkeeper.web.rest.response;
 
/**
 * Call Detail Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum CallDetailResponseCode implements IResponseCodeTypes{
	
    NO_CALL_DETAILS_FOUND(1300L),
    ALL_CALL_DETAILS_FROM_TERMINAL(1301L),
    SINGLE_CALL_DETAIL(1302L),
    NO_CALL_DETAIL_FOUND(1303L),
    ALL_CALL_DETAILS_DELETED(1304L),
    SINGLE_CALL_DETAIL_DELETED(1305L),
    CALL_DETAILS_DELETED(1306L),
    CALLS_SAVED(1307L),
    CALL_SAVED(1308L);
	
	private Long code;
	
	private CallDetailResponseCode(Long code) {
		this.code = code;
	}

	@Override
	public Long getResponseCode() {
	     return code;
	}
}
