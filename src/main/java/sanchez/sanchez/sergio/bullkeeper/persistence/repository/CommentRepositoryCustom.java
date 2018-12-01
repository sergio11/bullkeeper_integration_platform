package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsByKidDTO;


/**
 *
 * @author sergio
 */
public interface CommentRepositoryCustom {
	
	/**
	 * 
	 * @return
	 */
    List<CommentsByKidDTO> getCommentsByKid();
    
    /**
     * 
     * @param type
     * @param from
     * @param to
     * @param ids
     */
    void updateAnalysisStatusFor(final AnalysisTypeEnum type, final AnalysisStatusEnum from, 
    		final AnalysisStatusEnum to, final Collection<ObjectId> ids);
    
    /**
     * 
     * @param type
     * @param from
     * @param to
     */
    void updateAnalysisStatusFor(final AnalysisTypeEnum type, final AnalysisStatusEnum from,
    		final AnalysisStatusEnum to);
    
    /**
     * 
     * @param type
     * @param status
     * @param ids
     */
    void updateAnalysisStatusFor(final AnalysisTypeEnum type, final AnalysisStatusEnum status,
    		final Collection<ObjectId> ids);
    
    /**
     * 
     * @param type
     * @param ids
     */
    void startAnalysisFor(final AnalysisTypeEnum type, final Collection<ObjectId> ids);
    
    /**
     * 
     * @param ids
     */
    void startSentimentAnalysisFor(final Collection<ObjectId> ids);
    
    /**
     * 
     * @param ids
     */
    void startViolenceAnalysisFor(final Collection<ObjectId> ids);
    
    /**
     * 
     * @param ids
     */
    void startDrugsAnalysisFor(final Collection<ObjectId> ids);
    
    /**
     * 
     * @param ids
     */
    void startAdultAnalysisFor(final Collection<ObjectId> ids);
    
    /**
     * 
     * @param ids
     */
    void startBullyingAnalysisFor(final Collection<ObjectId> ids);
    
    /**
     * 
     * @param type
     * @param hours
     */
    void cancelAnalyzesThatAreTakingMoreThanNHours(final AnalysisTypeEnum type, 
    		final Integer hours);
    
    /**
     * 
     * @param socialMedia
     * @param kid
     * @return
     */
    Date getExtractedAtOfTheLastCommentBySocialMediaAndKidId(final SocialMediaTypeEnum socialMedia, 
    		final ObjectId kid);
    
    /**
     * 
     * @param kid
     * @param author
     * @param from
     * @param socialMedias
     * @param violence
     * @param drugs
     * @param bullying
     * @param adult
     * @return
     */
    List<CommentEntity> getComments(final ObjectId kid, final String author, final Date from, 
    		final SocialMediaTypeEnum[] socialMedias, final ViolenceLevelEnum violence,
    		final DrugsLevelEnum drugs, final BullyingLevelEnum bullying, final AdultLevelEnum adult);
    
    /**
     * 
     * @param identities
     * @param author
     * @param from
     * @param socialMedias
     * @param violence
     * @param drugs
     * @param bullying
     * @param adult
     * @return
     */
    List<CommentEntity> getComments(List<ObjectId> identities, String author, Date from, SocialMediaTypeEnum[] socialMedias,
			ViolenceLevelEnum violence, DrugsLevelEnum drugs, BullyingLevelEnum bullying, AdultLevelEnum adult);
}
