package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaDTO;

/**
 * Social Media Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface ISocialMediaService {
	
	/**
	 * Get Social Media By Kid
	 * @param id
	 * @return
	 */
    List<SocialMediaDTO> getSocialMediaByKid(final String id);
    
    /**
     * Get Social Media By Id
     * @param id
     * @return
     */
    SocialMediaDTO getSocialMediaById(final String id);
    
    /**
     * Get Social Media By Type And Kid Id
     * @param type
     * @param kid
     * @return
     */
    SocialMediaDTO getSocialMediaByTypeAndKidId(final String type, final String kid);
    
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
     * Delete Social Media By Kid
     * @param id
     * @return
     */
    Long deleteSocialMediaByKid(final String id);
    
    /**
     * Delete Social Media By ID
     * @param id
     * @return
     */
    Boolean deleteSocialMediaById(final String id);
}
