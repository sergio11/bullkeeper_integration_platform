package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public enum CommonErrorResponseCode implements IResponseCodeTypes {

    ACCESS_DENIED(500L), 
    SECURITY_ERROR(501L),
    GENERIC_ERROR(502L), 
    RESOURCE_NOT_FOUND(503L),
    USER_NOT_FOUND(504L),
    VALIDATION_ERROR(505L),
    ACCOUNT_LOCKED(505L), 
    MESSAGE_NOT_READABLE(506L), 
    ACCOUNT_PENDING_TO_BE_REMOVE(507L),
    AUTHENTICATION_FAILED_EXCEPTION(508L);

    private Long code;

    private CommonErrorResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }
}
