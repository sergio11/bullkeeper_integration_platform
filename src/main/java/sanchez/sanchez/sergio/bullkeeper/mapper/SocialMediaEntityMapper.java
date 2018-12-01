package sanchez.sanchez.sergio.bullkeeper.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.integration.service.IIntegrationFlowService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaDTO;

/**
 * @author sergio
 */
@Mapper
public abstract class SocialMediaEntityMapper {
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Integration Flow Service
	 */
	@Autowired
	protected IIntegrationFlowService itegrationFlowService;
	
	/**
	 * Comment Repository
	 */
	@Autowired
	protected CommentRepository commentRepository;
    
    @Mappings({
        @Mapping(expression="java(socialMediaEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(socialMediaEntity.getType().name())", target = "type" ),
        @Mapping(expression="java(socialMediaEntity.getKid().getId().toString())", target = "kid" )
    })
    @Named("socialMediaEntityToSocialMediaDTO")
    public abstract SocialMediaDTO socialMediaEntityToSocialMediaDTO(SocialMediaEntity socialMediaEntity); 
	
    @IterableMapping(qualifiedByName = "socialMediaEntityToSocialMediaDTO")
    public abstract List<SocialMediaDTO> socialMediaEntitiesToSocialMediaDTO(List<SocialMediaEntity> socialMediaEntities);
    
    @IterableMapping(qualifiedByName = "socialMediaEntityToSocialMediaDTO")
    public abstract List<SocialMediaDTO> socialMediaEntitiesToSocialMediaDTO(Iterable<SocialMediaEntity> socialMediaEntities);
    
    @Mappings({
    	@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum.valueOf(saveSocialMediaDTO.getType()))", target = "type" ),
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveSocialMediaDTO.getKid())))", target = "kid" ),
    	@Mapping(expression="java(itegrationFlowService.getDateForNextPoll().getTime())", target = "scheduledFor" ),
    	@Mapping(expression="java(commentRepository.getExtractedAtOfTheLastCommentBySocialMediaAndKidId(sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum.valueOf(saveSocialMediaDTO.getType()) ,new org.bson.types.ObjectId(saveSocialMediaDTO.getKid())))", target = "lastProbing" )
    })
    @Named("addSocialMediaDTOToSocialMediaEntity")
    public abstract SocialMediaEntity addSocialMediaDTOToSocialMediaEntity(SaveSocialMediaDTO saveSocialMediaDTO);
    
    @IterableMapping(qualifiedByName = "addSocialMediaDTOToSocialMediaEntity")
    public abstract List<SocialMediaEntity> addSocialMediaDTOToSocialMediaEntity(List<SaveSocialMediaDTO> saveSocialMediaEntities);
    
    @IterableMapping(qualifiedByName = "addSocialMediaDTOToSocialMediaEntity")
    public abstract List<SocialMediaEntity> addSocialMediaDTOToSocialMediaEntity(Iterable<SaveSocialMediaDTO> saveSocialMediaEntities);
    
}
