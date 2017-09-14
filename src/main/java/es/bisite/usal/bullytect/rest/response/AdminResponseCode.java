package es.bisite.usal.bullytect.rest.response;

public enum AdminResponseCode implements IResponseCodeTypes {

    AUTHENTICATION_SUCCESS(900L), BAD_CREDENTIALS(901L), SELF_ADMIN(902L), ADMIN_NOT_FOUND(903L);

    private Long code;

    private AdminResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
