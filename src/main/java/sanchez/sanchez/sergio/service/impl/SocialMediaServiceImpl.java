package sanchez.sanchez.sergio.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.CommentDTO;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.mapper.SocialMediaEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
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
        List<SocialMediaEntity> socialMediaEntities = socialMediaRepository.findBySonEntityId(new ObjectId(id));
        return socialMediaMapper.socialMediaEntitiesToSocialMediaDTO(socialMediaEntities);
    }   
    
    @Override
    public SocialMediaDTO getSocialMediaById(String id) {
        SocialMediaEntity socialMediaEntity = socialMediaRepository.findOne(new ObjectId(id));
        return socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntity);
    }
    
   
	@Override
	public SocialMediaDTO create(SaveSocialMediaDTO addSocialMediaDTO) {
		SocialMediaEntity socialMediaEntityToSave = socialMediaMapper.addSocialMediaDTOToSocialMediaEntity(addSocialMediaDTO);
		SocialMediaEntity socialMediaEntitySaved = socialMediaRepository.save(socialMediaEntityToSave);
		return socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntitySaved);
	}
	
	@Override
	public List<SocialMediaDTO> getInvalidSocialMediaById(String id) {
		List<SocialMediaEntity> socialMediaEntities = socialMediaRepository.findByIdAndInvalidTokenTrue(new ObjectId(id));
		return socialMediaMapper.socialMediaEntitiesToSocialMediaDTO(socialMediaEntities);
	}
	
	@Override
	public Page<SocialMediaDTO> findPaginated(Pageable pageable) {
		Page<SocialMediaEntity> socialMediaEntities =  socialMediaRepository.findAll(pageable);
        return socialMediaEntities.map(new Converter<SocialMediaEntity, SocialMediaDTO>(){
            @Override
            public SocialMediaDTO convert(SocialMediaEntity socialMediaEntity) {
               return socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntity);
            }
        });
	}
	
	@Override
	public SocialMediaDTO getSocialMediaByTypeAndSonId(String type, String sonId) {
		return socialMediaMapper.socialMediaEntityToSocialMediaDTO(
				socialMediaRepository.findByTypeAndSonEntityId(SocialMediaTypeEnum.valueOf(type), new ObjectId(sonId)));
	}
	
	@Override
	public SocialMediaDTO update(SaveSocialMediaDTO socialMedia) {
		Assert.notNull(socialMedia, "Social Media can not be null");
		SocialMediaDTO socialMediaUpdated = null;
		SocialMediaEntity socialMediaEntityToUpdate = socialMediaRepository.findByTypeAndSonEntityId(
				SocialMediaTypeEnum.valueOf(socialMedia.getType()), new ObjectId(socialMedia.getSon()));
		if(socialMediaEntityToUpdate != null) {
			socialMediaEntityToUpdate.setAccessToken(socialMedia.getAccessToken());
			SocialMediaEntity socialMediaEntityUpdated = socialMediaRepository.save(socialMediaEntityToUpdate);
			socialMediaUpdated = socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntityUpdated);
		}
		return socialMediaUpdated;
	}
	
	@Override
	public SocialMediaDTO save(SaveSocialMediaDTO socialMedia) {
		Assert.notNull(socialMedia, "Social Media can not be null");
		SocialMediaEntity socialMediaEntityToSave = socialMediaRepository.findByTypeAndSonEntityId(
				SocialMediaTypeEnum.valueOf(socialMedia.getType()), new ObjectId(socialMedia.getSon()));
		if(socialMediaEntityToSave != null) {
			socialMediaEntityToSave.setAccessToken(socialMedia.getAccessToken());
		} else {
			socialMediaEntityToSave= socialMediaMapper.addSocialMediaDTOToSocialMediaEntity(socialMedia);
		}
		SocialMediaEntity socialMediaEntitySaved = socialMediaRepository.save(socialMediaEntityToSave);
		return socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntitySaved);
	}
	
	@PostConstruct
    protected void init(){
        Assert.notNull(socialMediaRepository, "Social Media Repository cannot be null");
        Assert.notNull(socialMediaMapper, "Social Media Mapper cannot be null");
	}
}
