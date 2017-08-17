package sanchez.sanchez.sergio.rest.response;

public enum CommonErrorResponseCode implements IResponseCodeTypes {

    ACCESS_DENIED(500L), SECURITY_ERROR(501L),
    GENERIC_ERROR(502L), RESOURCE_NOT_FOUND(503L),
    USER_NOT_FOUND(504L), VALIDATION_ERROR(505L),
    ACCOUNT_LOCKED(505L), MESSAGE_NOT_READABLE(506L);

    private Long code;

    private CommonErrorResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }
}
