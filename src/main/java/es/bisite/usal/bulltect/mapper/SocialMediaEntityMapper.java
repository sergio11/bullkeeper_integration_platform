package es.bisite.usal.bulltect.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bulltect.integration.service.IItegrationFlowService;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaEntity;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.web.dto.request.SaveSocialMediaDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaDTO;

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
    	@Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum.valueOf(saveSocialMediaDTO.getType()))", target = "type" ),
    	@Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(saveSocialMediaDTO.getSon())))", target = "sonEntity" ),
    	@Mapping(expression="java(itegrationFlowService.getDateForNextPoll().getTime())", target = "scheduledFor" )
    })
    public abstract SocialMediaEntity addSocialMediaDTOToSocialMediaEntity(SaveSocialMediaDTO saveSocialMediaDTO);
    
    
    
}
