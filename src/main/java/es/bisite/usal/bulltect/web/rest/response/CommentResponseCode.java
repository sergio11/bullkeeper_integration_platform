package es.bisite.usal.bulltect.web.rest.response;

public enum CommentResponseCode implements IResponseCodeTypes {

    ALL_COMMENTS(200L), SINGLE_COMMENT(201L), COMMENT_NOT_FOUND(202L), 
    ALL_COMMENTS_BY_CHILD(203L), COMMENTS_BY_CHILD_NOT_FOUND(204L), COMMENTS_NOT_FOUND(205L),
    COMMENTS_EXTRACTED_BY_SON(206L), SOCIAL_MEDIA_LIKES(207L), MOST_ACTIVE_FRIENDS(208L), NEW_FRIENDS(209L),
    NO_COMMENTS_EXTRACTED(210L), NO_LIKES_FOUND_IN_THIS_PERIOD(211L), NO_ACTIVE_FRIENDS_IN_THIS_PERIOD(212L),
    NO_NEW_FRIENDS_AT_THIS_TIME(213L), FILTERED_COMMENTS(214L);
	
    private Long code;
    
    private CommentResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
