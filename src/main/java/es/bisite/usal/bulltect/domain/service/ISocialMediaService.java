package es.bisite.usal.bulltect.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bulltect.web.dto.request.SaveSocialMediaDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaDTO;

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
    SocialMediaDTO insertOrUpdate(SaveSocialMediaDTO socialMedia);
    Iterable<SocialMediaDTO> insertOrUpdate(Iterable<SaveSocialMediaDTO> socialMediaList);
    Iterable<SocialMediaDTO> save(Iterable<SaveSocialMediaDTO> socialMediaList, String sonId);
    List<SocialMediaDTO> getInvalidSocialMediaById(String id);
    List<SocialMediaDTO> getValidSocialMediaById(String id);
    Page<SocialMediaDTO> findPaginated(Pageable pageable);
    Long deleteSocialMediaByUser(String id);
    Boolean deleteSocialMediaById(String id);
}
