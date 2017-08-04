package sanchez.sanchez.sergio.rest.response;

public enum UserResponseCode implements IResponseCodeTypes {

    ALL_USERS(300L), SINGLE_USER(301L),
    USER_CREATED(302L), USER_NOT_FOUND(303L);

    private Long code;

    private UserResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
