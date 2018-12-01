package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public enum SchoolResponseCode implements IResponseCodeTypes {

    ALL_SCHOOLS(600L), 
    SINGLE_SCHOOL(601L),
    SCHOOLS_BY_NAME(602L), 
    SCHOOL_NOT_FOUND(603L),
    SCHOOL_SAVED(604L),
    SCHOOL_DELETED(605L), 
    NO_SCHOOLS_FOUND(606L),
    ALL_SCHOOLS_NAMES(607L), 
    TOTAL_NUMBER_OF_SCHOOLS(608L);

    private Long code;

    private SchoolResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
