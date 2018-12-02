package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;

/**
 * Kid Repository Custom
 * @author sergiosanchezsanchez
 *
 */
public interface KidRepositoryCustom {

	/**
	 * 
	 * @param id
	 * @param profileImageId
	 */
    void setProfileImageId(ObjectId id, String profileImageId);

    /**
     * 
     * @param id
     * @return
     */
    String getProfileImageIdByUserId(ObjectId id);
    
    /**
     * 
     * @param id
     * @param totalPositive
     * @param totalNegative
     * @param totalNeutro
     */
    void updateSentimentResultsFor(ObjectId id, long totalPositive, long totalNegative, long totalNeutro);
    
    /**
     * 
     * @param id
     * @param totalCommentsAdultContent
     * @param totalCommentsNoAdultContent
     */
    void updateAdultResultsFor(ObjectId id, long totalCommentsAdultContent, long totalCommentsNoAdultContent);
    
    /**
     * 
     * @param id
     * @param totalViolentComments
     * @param totalNonViolentComments
     */
    void updateViolenceResultsFor(ObjectId id, long totalViolentComments, long totalNonViolentComments);
    
    /**
     * 
     * @param id
     * @param totalCommentsBullying
     * @param totalCommentsNoBullying
     */
    void updateBullyingResultsFor(ObjectId id, long totalCommentsBullying, long totalCommentsNoBullying);
    
    /**
     * 
     * @param id
     * @param totalCommentsDrugs
     * @param totalCommentsNoDrugs
     */
    void updateDrugsResultsFor(ObjectId id, long totalCommentsDrugs, long totalCommentsNoDrugs);

    
}
