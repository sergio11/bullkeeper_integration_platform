package es.bisite.usal.bulltect.web.rest.response;

public enum ChildrenResponseCode implements IResponseCodeTypes {

    ALL_USERS(100L), SINGLE_USER(101L),
    USER_CREATED(102L), USER_NOT_FOUND(103L), NO_CHILDREN_FOUND(104L), 
    PROFILE_IMAGE_UPLOAD_SUCCESSFULLY(105L), ALERTS_BY_SON(106L), NO_ALERTS_BY_SON_FOUNDED(107L),
    CHILD_ALERTS_CLEANED(108L), GET_ALERT_BY_ID(109L), ALERT_NOT_FOUND(110L), ALERT_BY_ID_DELETED(111L),
    CHILD_DELETED_SUCCESSFULLY(112L);

    private Long code;

    private ChildrenResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
