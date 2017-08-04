package sanchez.sanchez.sergio.rest.response;

public enum CommentResponseCode implements IResponseCodeTypes {

    ALL_COMMENTS(200L), SINGLE_COMMENT(201L), COMMENT_NOT_FOUND(202L), 
    ALL_COMMENTS_BY_USER(203L), COMMENTS_BY_USER_NOT_FOUND(204L);

    private Long code;

    private CommentResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
