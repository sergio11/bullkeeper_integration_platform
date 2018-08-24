package sanchez.sanchez.sergio.masoc.domain.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SocialMediaDTO;

/**
 * Social Media Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface ISocialMediaService {
	
	/**
	 * Get Social Media By User
	 * @param id
	 * @return
	 */
    List<SocialMediaDTO> getSocialMediaByUser(final String id);
    
    /**
     * Get Social Media By Id
     * @param id
     * @return
     */
    SocialMediaDTO getSocialMediaById(final String id);
    
    /**
     * Get Social Media By Type And Son Id
     * @param type
     * @param sonId
     * @return
     */
    SocialMediaDTO getSocialMediaByTypeAndSonId(final String type, final String sonId);
    
    /**
     * Update
     * @param socialMedia
     * @return
     */
    SocialMediaDTO update(final SaveSocialMediaDTO socialMedia);
    
    /**
     * Create
     * @param socialMedia
     * @return
     */
    SocialMediaDTO create(final SaveSocialMediaDTO socialMedia);
    
    /**
     * Insert or update
     * @param socialMedia
     * @return
     */
    SocialMediaDTO insertOrUpdate(final SaveSocialMediaDTO socialMedia);
    
    /**
     * Insert or update
     * @param socialMediaList
     * @return
     */
    Iterable<SocialMediaDTO> insertOrUpdate(final Iterable<SaveSocialMediaDTO> socialMediaList);
    
    /**
     * Save
     * @param socialMediaList
     * @param sonId
     * @return
     */
    Iterable<SocialMediaDTO> save(final List<SaveSocialMediaDTO> socialMediaList, 
    		final String sonId);
    
    /**
     * Get Invalid Social Media By Id
     * @param id
     * @return
     */
    List<SocialMediaDTO> getInvalidSocialMediaById(final String id);
    
    /**
     * Get Valid Social Media By Id
     * @param id
     * @return
     */
    List<SocialMediaDTO> getValidSocialMediaById(final String id);
    
    /**
     * Find Paginated
     * @param pageable
     * @return
     */
    Page<SocialMediaDTO> findPaginated(final Pageable pageable);
    
    /**
     * Delete Social Media By User
     * @param id
     * @return
     */
    Long deleteSocialMediaByUser(final String id);
    
    /**
     * Delete Social Media By ID
     * @param id
     * @return
     */
    Boolean deleteSocialMediaById(final String id);
}
