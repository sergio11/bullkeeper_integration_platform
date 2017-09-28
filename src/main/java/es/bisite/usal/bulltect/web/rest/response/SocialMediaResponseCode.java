package es.bisite.usal.bulltect.web.rest.response;

public enum SocialMediaResponseCode implements IResponseCodeTypes {

    SOCIAL_MEDIA_BY_CHILD(300L), SINGLE_SOCIAL_MEDIA(301L), 
    SOCIAL_MEDIA_BY_CHILD_NOT_FOUND(302L), SOCIAL_MEDIA_ADDED(303L),
    INVALID_SOCIAL_MEDIA_BY_CHILD(304L), ALL_SOCIAL_MEDIA(305L), SOCIAL_MEDIA_UPDATED(306L),
    SOCIAL_MEDIA_SAVED(307L), ALL_USER_SOCIAL_MEDIA_DELETED(308L), USER_SOCIAL_MEDIA_DELETED(309L),
    VALID_SOCIAL_MEDIA_BY_CHILD(310L), ALL_SOCIAL_MEDIA_SAVED(311L);
	
    private Long code;

    private SocialMediaResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
