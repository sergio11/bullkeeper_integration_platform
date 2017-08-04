package sanchez.sanchez.sergio.rest.response;

public enum UserResponseCode implements IResponseCodeTypes {

    ALL_USERS(100L), SINGLE_USER(101L),
    USER_CREATED(102L), USER_NOT_FOUND(103L);

    private Long code;

    private UserResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
