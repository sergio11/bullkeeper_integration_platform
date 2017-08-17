package sanchez.sanchez.sergio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;

/**
 *
 * @author sergio
 */
public interface ISocialMediaService {
    List<SocialMediaDTO> getSocialMediaByUser(String id);
    SocialMediaDTO getSocialMediaById(String id);
    SocialMediaDTO getSocialMediaByTypeAndSonId(String type, String sonId);
    SocialMediaDTO update(SaveSocialMediaDTO socialMedia);
    SocialMediaDTO create(SaveSocialMediaDTO socialMedia);
    SocialMediaDTO save(SaveSocialMediaDTO socialMedia);
    List<SocialMediaDTO> getInvalidSocialMediaById(String id);
    Page<SocialMediaDTO> findPaginated(Pageable pageable);
    List<SocialMediaDTO> deleteSocialMediaByUser(String id);
    SocialMediaDTO deleteSocialMediaById(String id);
}
