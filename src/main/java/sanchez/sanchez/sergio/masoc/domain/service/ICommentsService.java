package sanchez.sanchez.sergio.masoc.domain.service;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.masoc.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.masoc.web.dto.response.CommentDTO;

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
     * Get Comment By Son Id Paginated
     * @param pageable
     * @param userId
     * @return
     */
    Page<CommentDTO> getCommentBySonIdPaginated(final Pageable pageable, final String userId);
    
    /**
     * Get Comments By Son Id
     * @param userId
     * @return
     */
    Iterable<CommentDTO> getCommentBySonId(final String userId);
    
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
     * @return
     */
    Iterable<CommentDTO> getComments(final List<String> identities, final String author, 
    		final Date from, final SocialMediaTypeEnum[] socialMedias, final ViolenceLevelEnum violence, 
    		final DrugsLevelEnum drugs, final BullyingLevelEnum bullying, final AdultLevelEnum adult);
    
    /**
     * Get Comments
     * @param idSon
     * @param author
     * @param from
     * @param socialMedias
     * @param violence
     * @param drugs
     * @param bullying
     * @param adult
     * @return
     */
    Iterable<CommentDTO> getComments(final String idSon, final String author, final Date from,
    		final SocialMediaTypeEnum[] socialMedias, final ViolenceLevelEnum violence, final DrugsLevelEnum drugs, BullyingLevelEnum bullying,
    		final AdultLevelEnum adult);
}
