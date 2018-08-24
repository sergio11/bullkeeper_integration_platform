package sanchez.sanchez.sergio.masoc.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.masoc.domain.service.ISocialMediaService;
import sanchez.sanchez.sergio.masoc.integration.service.IIntegrationFlowService;
import sanchez.sanchez.sergio.masoc.mapper.SocialMediaEntityMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.masoc.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.masoc.rrss.service.IFacebookService;
import sanchez.sanchez.sergio.masoc.rrss.service.IGoogleService;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SocialMediaDTO;

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
    private final IIntegrationFlowService itegrationFlowService;
    private final IFacebookService facebookService;
    private final IGoogleService googleService;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository,
            SocialMediaEntityMapper socialMediaMapper, IIntegrationFlowService itegrationFlowService, 
            IFacebookService facebookService, IGoogleService googleService) {
        this.socialMediaRepository = socialMediaRepository;
        this.socialMediaMapper = socialMediaMapper;
        this.itegrationFlowService = itegrationFlowService;
        this.facebookService = facebookService;
        this.googleService = googleService;
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
        	socialMediaEntityToUpdate.setUserSocialName(getUserNameForSocialMedia(socialMediaEntityToUpdate.getType(), socialMedia.getAccessToken()));
        	socialMediaEntityToUpdate.setUserPicture(getUserPictureForSocialMedia(socialMediaEntityToUpdate.getType(), socialMedia.getAccessToken()));
            socialMediaEntityToUpdate.setAccessToken(socialMediaEntityToUpdate.getType().equals(SocialMediaTypeEnum.FACEBOOK) ? 
            		facebookService.obtainExtendedAccessToken(socialMedia.getAccessToken()): socialMedia.getAccessToken());
            socialMediaEntityToUpdate.setRefreshToken(socialMedia.getRefreshToken());
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
        	socialMediaEntityToSave.setUserSocialName(getUserNameForSocialMedia(socialMediaEntityToSave.getType(), socialMedia.getAccessToken()));
        	socialMediaEntityToSave.setUserPicture(getUserPictureForSocialMedia(socialMediaEntityToSave.getType(), socialMedia.getAccessToken()));
            socialMediaEntityToSave.setAccessToken(socialMediaEntityToSave.getType().equals(SocialMediaTypeEnum.FACEBOOK) ? 
            		facebookService.obtainExtendedAccessToken(socialMedia.getAccessToken()): socialMedia.getAccessToken());
            socialMediaEntityToSave.setRefreshToken(socialMedia.getRefreshToken());
            socialMediaEntityToSave.setScheduledFor(itegrationFlowService.getDateForNextPoll().getTime());
        } else {
            socialMediaEntityToSave = socialMediaMapper.addSocialMediaDTOToSocialMediaEntity(socialMedia);
            socialMediaEntityToSave.setUserSocialName(getUserNameForSocialMedia(socialMediaEntityToSave.getType(), socialMedia.getAccessToken()));
            socialMediaEntityToSave.setUserPicture(getUserPictureForSocialMedia(socialMediaEntityToSave.getType(), socialMedia.getAccessToken()));
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
                // already exists
                final SocialMediaEntity currentSocialMedia = currentSocialMedias.get(i);
                final SaveSocialMediaDTO newSocialMedia = socialMediaList.get(j);
                
                // check if it is necessary to update the token
                if(!currentSocialMedia.getAccessToken().equals(newSocialMedia.getAccessToken())) {
                	currentSocialMedia.setUserSocialName(getUserNameForSocialMedia(currentSocialMedia.getType(), newSocialMedia.getAccessToken()));
                	currentSocialMedia.setUserPicture(getUserPictureForSocialMedia(currentSocialMedia.getType(), newSocialMedia.getAccessToken()));
                    currentSocialMedia.setAccessToken(currentSocialMedia.getType().equals(SocialMediaTypeEnum.FACEBOOK) ? 
                    		facebookService.obtainExtendedAccessToken(newSocialMedia.getAccessToken()): newSocialMedia.getAccessToken());
                    currentSocialMedia.setRefreshToken(newSocialMedia.getRefreshToken());
                    currentSocialMedia.setInvalidToken(Boolean.FALSE);
                    currentSocialMedia.setScheduledFor(itegrationFlowService.getDateForNextPoll().getTime());
                }
                
                result.add(currentSocialMedia);
            
            } else if(i == -1 && j >= 0) {
            	// Add New Social Media
                final SocialMediaEntity newSocialMedia = socialMediaMapper.addSocialMediaDTOToSocialMediaEntity(socialMediaList.get(j));
                newSocialMedia.setUserSocialName(getUserNameForSocialMedia(newSocialMedia.getType(), newSocialMedia.getAccessToken()));
                newSocialMedia.setUserPicture(getUserPictureForSocialMedia(newSocialMedia.getType(), newSocialMedia.getAccessToken()));
                logger.debug(newSocialMedia.toString());
                result.add(newSocialMedia);
            } else if ( i >= 0 && j == -1) {
            	// delete social media
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
    
    
    /**
     * Gets the name of the user who owns the access token in the specified social media. 
     * @param socialMedia
     * @param accessToken
     * @return
     */
    private String getUserNameForSocialMedia(final SocialMediaTypeEnum socialMedia, final String accessToken){
    	
    	String userSocialName = null;
    	
    	try {
    		
    		if(socialMedia.equals(SocialMediaTypeEnum.FACEBOOK))
        		userSocialName = facebookService.getUserNameForAccessToken(accessToken);
        	else if(socialMedia.equals(SocialMediaTypeEnum.YOUTUBE))
        		userSocialName = googleService.getUserNameForAccessToken(accessToken);
        	else
        		userSocialName = "";
    		
    	} catch (Exception ex) {
    		logger.error(ex.getMessage());
    	}
    	
    	return userSocialName;
    	
    }
    
    /**
     * Gets the image of the user who owns the access token in the specified social media.
     * @param socialMedia
     * @param accessToken
     * @return
     */
    private String getUserPictureForSocialMedia(final SocialMediaTypeEnum socialMedia, final String accessToken){
    	
    	String userPicture = null;
    	
    	try {
    		
    		if(socialMedia.equals(SocialMediaTypeEnum.FACEBOOK))
    			userPicture = facebookService.fetchUserPicture(accessToken);
        	else if(socialMedia.equals(SocialMediaTypeEnum.YOUTUBE))
        		userPicture = googleService.getUserImage(accessToken);
        	else
        		userPicture = "";
    		
    	} catch (Exception ex) {
    		logger.error(ex.getMessage());
    	}
    	
    	return userPicture;
    	
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(socialMediaRepository, "Social Media Repository cannot be null");
        Assert.notNull(socialMediaMapper, "Social Media Mapper cannot be null");
        Assert.notNull(itegrationFlowService, "Integration Flow Service can not be null");
    }
}
