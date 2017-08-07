package sanchez.sanchez.sergio.rest.response;

public enum SchoolResponseCode implements IResponseCodeTypes {

    ALL_SCHOOLS(600L), SINGLE_SCHOOL(601L), SCHOOLS_BY_NAME(602L), SCHOOL_NOT_FOUND(603L); 

    private Long code;

    private SchoolResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
