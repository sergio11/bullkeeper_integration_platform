package es.bisite.usal.bulltect.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import es.bisite.usal.bulltect.domain.service.ISocialMediaService;
import es.bisite.usal.bulltect.integration.service.IItegrationFlowService;
import es.bisite.usal.bulltect.mapper.SocialMediaEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.persistence.repository.SocialMediaRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.web.dto.request.SaveSocialMediaDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaDTO;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sergio
 */
@Service
public class SocialMediaServiceImpl implements ISocialMediaService {
    
    private Logger logger = LoggerFactory.getLogger(SocialMediaServiceImpl.class);

    private final SocialMediaRepository socialMediaRepository;
    private final SocialMediaEntityMapper socialMediaMapper;
    private final IItegrationFlowService itegrationFlowService;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository,
            SocialMediaEntityMapper socialMediaMapper, IItegrationFlowService itegrationFlowService) {
        this.socialMediaRepository = socialMediaRepository;
        this.socialMediaMapper = socialMediaMapper;
        this.itegrationFlowService = itegrationFlowService;
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
        Page<SocialMediaEntity> socialMediaEntities = socialMediaRepository.findAll(pageable);
        return socialMediaEntities.map(new Converter<SocialMediaEntity, SocialMediaDTO>() {
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
        if (socialMediaEntityToUpdate != null) {
            socialMediaEntityToUpdate.setAccessToken(socialMedia.getAccessToken());
            socialMediaEntityToUpdate.setScheduledFor(itegrationFlowService.getDateForNextPoll().getTime());
            SocialMediaEntity socialMediaEntityUpdated = socialMediaRepository.save(socialMediaEntityToUpdate);
            socialMediaUpdated = socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntityUpdated);
        }
        return socialMediaUpdated;
    }

    @Override
    public SocialMediaDTO insertOrUpdate(SaveSocialMediaDTO socialMedia) {
        Assert.notNull(socialMedia, "Social Media can not be null");
        SocialMediaEntity socialMediaEntityToSave = socialMediaRepository.findByTypeAndSonEntityId(
                SocialMediaTypeEnum.valueOf(socialMedia.getType()), new ObjectId(socialMedia.getSon()));
        if (socialMediaEntityToSave != null) {
            socialMediaEntityToSave.setAccessToken(socialMedia.getAccessToken());
            socialMediaEntityToSave.setScheduledFor(itegrationFlowService.getDateForNextPoll().getTime());
        } else {
            socialMediaEntityToSave = socialMediaMapper.addSocialMediaDTOToSocialMediaEntity(socialMedia);
        }
        SocialMediaEntity socialMediaEntitySaved = socialMediaRepository.save(socialMediaEntityToSave);
        return socialMediaMapper.socialMediaEntityToSocialMediaDTO(socialMediaEntitySaved);
    }

    @Override
    public Iterable<SocialMediaDTO> insertOrUpdate(Iterable<SaveSocialMediaDTO> socialMediaList) {
        List<SocialMediaDTO> socialMediaDTOs = new ArrayList<>();
        for (SaveSocialMediaDTO socialMedia : socialMediaList) {
            socialMediaDTOs.add(insertOrUpdate(socialMedia));
        }
        return socialMediaDTOs;
    }

    @Override
    public Iterable<SocialMediaDTO> save(List<SaveSocialMediaDTO> socialMediaList, String sonId) {
        
        List<SocialMediaEntity> result = new ArrayList<SocialMediaEntity>();
        
        List<SocialMediaEntity> currentSocialMedias = socialMediaRepository.findBySonEntityId(new ObjectId(sonId));
    
        for(SocialMediaTypeEnum SocialMediaType: SocialMediaTypeEnum.values()) {
            
            
            // get index on current social media list
            int i = currentSocialMedias.stream()
                    .map(socialMedia -> socialMedia.getType().name()).collect(Collectors.toList())
                    .indexOf(SocialMediaType.name());
            
            
            // get index on new social media list
            int j = socialMediaList.stream()
                    .map(socialMedia -> socialMedia.getType()).collect(Collectors.toList())
                    .indexOf(SocialMediaType.name());
            
            if(i >= 0 && j >= 0) {
                
                final SocialMediaEntity currentSocialMedia = currentSocialMedias.get(i);
                final SaveSocialMediaDTO newSocialMedia = socialMediaList.get(j);
                
                if(!currentSocialMedia.getAccessToken().equals(newSocialMedia.getAccessToken())) {
                    currentSocialMedia.setAccessToken(newSocialMedia.getAccessToken());
                    currentSocialMedia.setInvalidToken(Boolean.FALSE);
                    currentSocialMedia.setScheduledFor(itegrationFlowService.getDateForNextPoll().getTime());
                }
                
                result.add(currentSocialMedia);
            
            } else if(i == -1 && j >= 0) {
                final SocialMediaEntity newSocialMedia = socialMediaMapper.addSocialMediaDTOToSocialMediaEntity(socialMediaList.get(j));
                logger.debug(newSocialMedia.toString());
                result.add(newSocialMedia);
            } else if ( i >= 0 && j == -1) {
                final SocialMediaEntity currentSocialMedia = currentSocialMedias.get(i);
                socialMediaRepository.delete(currentSocialMedia);
            } 
        
        }
  
        return socialMediaMapper.socialMediaEntitiesToSocialMediaDTO(socialMediaRepository.save(result));
       

    }

    @Override
    public Long deleteSocialMediaByUser(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can not be empty");
        return socialMediaRepository.deleteBySonEntityId(new ObjectId(id));
    }

    @Override
    public Boolean deleteSocialMediaById(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can not be empty");
        return socialMediaRepository.deleteById(new ObjectId(id)) > 0;
    }

    @Override
    public List<SocialMediaDTO> getValidSocialMediaById(String id) {
        List<SocialMediaEntity> socialMediaEntities = socialMediaRepository.findByIdAndInvalidTokenFalse(new ObjectId(id));
        return socialMediaMapper.socialMediaEntitiesToSocialMediaDTO(socialMediaEntities);
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(socialMediaRepository, "Social Media Repository cannot be null");
        Assert.notNull(socialMediaMapper, "Social Media Mapper cannot be null");
        Assert.notNull(itegrationFlowService, "Integration Flow Service can not be null");
    }
}
