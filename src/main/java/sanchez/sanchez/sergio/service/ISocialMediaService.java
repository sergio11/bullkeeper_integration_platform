package sanchez.sanchez.sergio.service;

import java.util.List;

import sanchez.sanchez.sergio.dto.request.AddSocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;

/**
 *
 * @author sergio
 */
public interface ISocialMediaService {
    List<SocialMediaDTO> getSocialMediaByUser(String id);
    SocialMediaDTO getSocialMediaById(String id);
    SocialMediaDTO save(AddSocialMediaDTO addSocialMediaDTO);
    List<SocialMediaDTO> getInvalidSocialMediaById(String id);
}
