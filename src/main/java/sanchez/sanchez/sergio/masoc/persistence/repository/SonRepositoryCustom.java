package sanchez.sanchez.sergio.masoc.persistence.repository;

import org.bson.types.ObjectId;

public interface SonRepositoryCustom {

    void setProfileImageId(ObjectId id, String profileImageId);

    String getProfileImageIdByUserId(ObjectId id);
    void updateSentimentResultsFor(ObjectId id, long totalPositive, long totalNegative, long totalNeutro);
    void updateAdultResultsFor(ObjectId id, long totalCommentsAdultContent, long totalCommentsNoAdultContent);
    void updateViolenceResultsFor(ObjectId id, long totalViolentComments, long totalNonViolentComments);
    void updateBullyingResultsFor(ObjectId id, long totalCommentsBullying, long totalCommentsNoBullying);
    void updateDrugsResultsFor(ObjectId id, long totalCommentsDrugs, long totalCommentsNoDrugs);
}
