package sanchez.sanchez.sergio.bullkeeper.web.rest.response;
 
/**
 * Sms Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum SmsResponseCode implements IResponseCodeTypes{
	
    NO_SMS_FOUND(1400L),
    ALL_SMS_FROM_TERMINAL(1401L),
    SINGLE_SMS_DETAIL(1402L),
    SINGLE_SMS_NOT_FOUND(1403L),
    ALL_SMS_FROM_TERMINAL_DELETED(1404L),
    SINGLE_SMS_DELETED(1405L),
    SMS_SAVED_SUCCESSFULLY(1406L);
	
	private Long code;
	
	private SmsResponseCode(Long code) {
		this.code = code;
	}

	@Override
	public Long getResponseCode() {
	     return code;
	}
}
