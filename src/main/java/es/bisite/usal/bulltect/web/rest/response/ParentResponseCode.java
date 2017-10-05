package es.bisite.usal.bulltect.web.rest.response;

public enum ParentResponseCode implements IResponseCodeTypes {

    ALL_PARENTS(400L), SINGLE_PARENT(401L), 
    CHILDREN_OF_PARENT(402L), PARENT_REGISTERED_SUCCESSFULLY(403L), 
    ADDED_SON_TO_PARENT(404L), SELF_PARENT(405L), PARENT_NOT_FOUND(406L),
    ADDED_SON_TO_SELF_PARENT(407L), NO_CHILDREN_FOUND_FOR_SELF_PARENT(408L),
    NO_CHILDREN_FOUND_FOR_PARENT(409L), CHILDREN_OF_SELF_PARENT(410L), PARENTS_NOT_FOUND(411L),
    PARENT_RESET_PASSWORD_REQUEST(412L), PARENT_UPDATED_SUCCESSFULLY(413L), SELF_PARENT_UPDATED_SUCCESSFULLY(414L),
    AUTHENTICATION_SUCCESS(415L), BAD_CREDENTIALS(416L), ACCOUNT_DISABLED(417L), ACCOUNT_LOCKED(418L), ACCOUNT_UNLOCKED(419L),
    AUTHENTICATION_VIA_FACEBOOK_SUCCESS(420L), GET_INFORMATION_FROM_FACEBOOK_FAILED(421L), SUCCESSFUL_ACCOUNT_DELETION_REQUEST(422L),
    INVALID_FACEBOOK_ID(423L), USERNAME_NOT_FOUND(424L), PROFILE_IMAGE_UPLOAD_SUCCESSFULLY(425L), PROFILE_IMAGE_DELETED_SUCCESSFULLY(426L),
    SAVE_SON_INFORMATION(427L), ALERTS_FOR_SELF_PARENT(428L), LAST_ITERATIONS_FOR_SELF_PARENT(429L), NO_ITERATIONS_FOUND_FOR_SELF_PARENT(430L),
    LAST_ITERATION_FOR_SELF_PARENT(431L);

    private Long code;

    private ParentResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
