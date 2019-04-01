package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SentimentLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentDTO;

/**
 * Comments Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface ICommentsService {
    
	/**
	 * Get Total Comments
	 * @return
	 */
    Long getTotalComments();
    
    /**
     * Get Comment By Id
     * @param id
     * @return
     */
    CommentDTO getCommentById(final String id);
    
    /**
     * Find Paginated
     * @param page
     * @param size
     * @return
     */
    Page<CommentDTO> findPaginated(final Integer page, final Integer size);
    
    /**
     * Find Paginated
     * @param pageable
     * @return
     */
    Page<CommentDTO> findPaginated(final Pageable pageable);
    
    /**
     * Get Comment By Kid Id Paginated
     * @param pageable
     * @param id
     * @return
     */
    Page<CommentDTO> getCommentByKidIdPaginated(final Pageable pageable, final String id);
    
    /**
     * Get Comments By Kid Id
     * @param id
     * @return
     */
    Iterable<CommentDTO> getCommentByKidId(final String id);
    
    /**
     * Get Comments
     * @param identities
     * @param author
     * @param from
     * @param socialMedias
     * @param violence
     * @param drugs
     * @param bullying
     * @param adult
     * @param sentiment
     * @return
     */
    Iterable<CommentDTO> getComments(final List<String> identities, final String author, 
    		final Date from, final SocialMediaTypeEnum[] socialMedias, final ViolenceLevelEnum violence, 
    		final DrugsLevelEnum drugs, final BullyingLevelEnum bullying, final AdultLevelEnum adult,
    		final SentimentLevelEnum sentiment);
    
    /**
     * Get Comments
     * @param id
     * @param author
     * @param from
     * @param socialMedias
     * @param violence
     * @param drugs
     * @param bullying
     * @param adult
     * @param sentiment
     * @return
     */
    Iterable<CommentDTO> getComments(final String id, final String author, final Date from,
    		final SocialMediaTypeEnum[] socialMedias, final ViolenceLevelEnum violence, final DrugsLevelEnum drugs, BullyingLevelEnum bullying,
    		final AdultLevelEnum adult, final SentimentLevelEnum sentiment);
}
