package es.bisite.usal.bulltect.web.rest.response;

public enum ChildrenResponseCode implements IResponseCodeTypes {

    ALL_USERS(100L), SINGLE_USER(101L),
    USER_CREATED(102L), USER_NOT_FOUND(103L), NO_CHILDREN_FOUND(104L);


    private Long code;

    private ChildrenResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
