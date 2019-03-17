package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * Analysis Statistics Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum AnalysisStatisticsResponseCode implements IResponseCodeTypes {
	
	SOCIAL_MEDIA_ACTIVITY_STATISTICS(1800L), 
    SOCIAL_MEDIA_ACTIVITY_STATISTICS_NOT_FOUND(1801L),
    SENTIMENT_ANALYSIS_STATISTICS(1802L), 
    COMMUNITIES_STATISTICS(1803L), 
    FOUR_DIMENSIONS_STATISTICS(1804L),
    NO_SENTIMENT_ANALYSIS_STATISTICS_FOR_THIS_PERIOD(1805L), 
    NO_COMMUNITY_STATISTICS_FOR_THIS_PERIOD(1806L),
    NO_DIMENSIONS_STATISTICS_FOR_THIS_PERIOD(1807L),
    SOCIAL_MEDIA_LIKES(1808L),
    NO_LIKES_FOUND_IN_THIS_PERIOD(1809L),
    COMMENTS_EXTRACTED_BY_KID(1810L),
    NO_COMMENTS_EXTRACTED(1811L),
    COMMENTS_EXTRACTED_BY_SOCIAL_MEDIA(1812L),
    ANALYSIS_STATISTICS_SUMMARY(1813L),
    NO_ANALYSIS_STATISTICS_SUMMARY_FOUND(1814L);

	private Long code;

    private AnalysisStatisticsResponseCode(final Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }
}
