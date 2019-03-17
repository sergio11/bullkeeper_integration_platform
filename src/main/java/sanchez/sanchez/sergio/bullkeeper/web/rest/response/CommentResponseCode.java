package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public enum CommentResponseCode implements IResponseCodeTypes {

    ALL_COMMENTS(200L), 
    SINGLE_COMMENT(201L), 
    COMMENT_NOT_FOUND(202L), 
    ALL_COMMENTS_BY_CHILD(203L), 
    COMMENTS_BY_CHILD_NOT_FOUND(204L), 
    COMMENTS_NOT_FOUND(205L),
    FILTERED_COMMENTS(208L);
	
    private Long code;
    
    private CommentResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
