package sanchez.sanchez.sergio.rest.response;

public enum ParentResponseCode implements IResponseCodeTypes {

    ALL_PARENTS(400L), SINGLE_PARENT(401L), 
    CHILDREN_OF_PARENT(402L), 
    PARENT_REGISTERED_SUCCESSFULLY(403L), ADDED_SON(404L);

    private Long code;

    private ParentResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}