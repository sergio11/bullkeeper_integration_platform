package sanchez.sanchez.sergio.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.dto.request.AddSocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.persistence.repository.SonRepository;

/**
 * @author sergio
 */
@Mapper
public abstract class SocialMediaEntityMapper {
	
	@Autowired
	private SonRepository sonRepository;
    
    @Mappings({
        @Mapping(expression="java(socialMediaEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(socialMediaEntity.getType().name())", target = "type" ),
        @Mapping(source="socialMediaEntity.userEntity.fullName", target = "user" )
    })
    @Named("socialMediaEntityToSocialMediaDTO")
    public abstract SocialMediaDTO socialMediaEntityToSocialMediaDTO(SocialMediaEntity socialMediaEntity); 
	
    @IterableMapping(qualifiedByName = "socialMediaEntityToSocialMediaDTO")
    public abstract List<SocialMediaDTO> socialMediaEntitiesToSocialMediaDTO(List<SocialMediaEntity> socialMediaEntities);
    
    @Mappings({
    	@Mapping(expression="java(SocialMediaTypeEnum.valueOf(addSocialMediaDTO.type))", target = "type" ),
    	@Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(addSocialMediaDTO.getUser()))", target = "sonEntity" )
    })
    public abstract SocialMediaEntity addSocialMediaDTOToSocialMediaEntity(AddSocialMediaDTO addSocialMediaDTO);
    
    
    
}
