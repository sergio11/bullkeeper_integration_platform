package sanchez.sanchez.sergio.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.dto.request.AddSocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.mapper.SocialMediaEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.service.ISocialMediaService;

/**
 *
 * @author sergio
 */
@Service
public class SocialMediaServiceImpl implements ISocialMediaService {
    
    private final SocialMediaRepository socialMediaRepository;
    private final SocialMediaEntityMapper socialMediaMapper;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository, SocialMediaEntityMapper socialMediaMapper) {
        this.socialMediaRepository = socialMediaRepository;
        this.socialMediaMapper = socialMediaMapper;
    }
    
    @Override
    public List<SocialMediaDTO> getSocialMediaByUser(String id) {
        List<SocialMediaEntity> socialMediaEntities = socialMediaRepository.findByUserEntityId(new ObjectId(id));
        return socialMediaMapper.socialMediaEntitiesToSocialMediaDTO(socialMediaEntities);
    }   
    
    @Override
    public SocialMediaDTO getSocialMediaById(String id) {
        SocialMediaEntity socialMediaEntity = socialMediaRepository.findOne(new ObjectId(id));
        return socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntity);
    }
    
   
	@Override
	public SocialMediaDTO save(AddSocialMediaDTO addSocialMediaDTO) {
		SocialMediaEntity socialMediaEntityToSave = socialMediaMapper.addSocialMediaDTOToSocialMediaEntity(addSocialMediaDTO);
		SocialMediaEntity socialMediaEntitySaved = socialMediaRepository.save(socialMediaEntityToSave);
		return socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntitySaved);
	}
	
	@PostConstruct
    protected void init(){
        Assert.notNull(socialMediaRepository, "Social Media Repository cannot be null");
        Assert.notNull(socialMediaMapper, "Social Media Mapper cannot be null");
    }
}
