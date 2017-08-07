package sanchez.sanchez.sergio.rest.response;

public enum SocialMediaResponseCode implements IResponseCodeTypes {

    SOCIAL_MEDIA_BY_USER(300L), SINGLE_SOCIAL_MEDIA(301L), 
    SOCIAL_MEDIA_BY_USER_NOT_FOUND(302L), SOCIAL_MEDIA_ADDED(303L);

    private Long code;

    private SocialMediaResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
