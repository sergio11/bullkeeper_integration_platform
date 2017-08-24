package sanchez.sanchez.sergio.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.service.IItegrationFlowService;

/**
 * @author sergio
 */
@Mapper
public abstract class SocialMediaEntityMapper {
	
	@Autowired
	protected SonRepository sonRepository;
	
	@Autowired
	protected IItegrationFlowService itegrationFlowService;
    
    @Mappings({
        @Mapping(expression="java(socialMediaEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(socialMediaEntity.getType().name())", target = "type" ),
        @Mapping(source="socialMediaEntity.sonEntity.fullName", target = "user" )
    })
    @Named("socialMediaEntityToSocialMediaDTO")
    public abstract SocialMediaDTO socialMediaEntityToSocialMediaDTO(SocialMediaEntity socialMediaEntity); 
	
    @IterableMapping(qualifiedByName = "socialMediaEntityToSocialMediaDTO")
    public abstract List<SocialMediaDTO> socialMediaEntitiesToSocialMediaDTO(List<SocialMediaEntity> socialMediaEntities);
    
    @Mappings({
    	@Mapping(expression="java(sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum.valueOf(saveSocialMediaDTO.getType()))", target = "type" ),
    	@Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(saveSocialMediaDTO.getSon())))", target = "sonEntity" ),
    	@Mapping(expression="java(itegrationFlowService.getDateForNextPoll().getTime())", target = "scheduledFor" )
    })
    public abstract SocialMediaEntity addSocialMediaDTOToSocialMediaEntity(SaveSocialMediaDTO saveSocialMediaDTO);
    
    
    
}
