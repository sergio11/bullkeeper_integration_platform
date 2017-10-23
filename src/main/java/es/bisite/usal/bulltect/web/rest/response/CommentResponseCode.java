package es.bisite.usal.bulltect.web.rest.response;

public enum CommentResponseCode implements IResponseCodeTypes {

    ALL_COMMENTS(200L), SINGLE_COMMENT(201L), COMMENT_NOT_FOUND(202L), 
    ALL_COMMENTS_BY_CHILD(203L), COMMENTS_BY_CHILD_NOT_FOUND(204L), COMMENTS_NOT_FOUND(205L),
    COMMENTS_ANALYZED_BY_SON(206L), SOCIAL_MEDIA_LIKES(207L);

    private Long code;
    
    private CommentResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
