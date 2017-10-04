package es.bisite.usal.bulltect.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.repository.AuthorityRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByFacebookDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentDTO;
import es.bisite.usal.bulltect.web.dto.response.ParentDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ParentEntityMapper {
	
	@Autowired
    protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected SonRepository sonRepository;
	
	@Autowired
	protected AuthorityRepository authorityRepository;
    
    @Mappings({
        @Mapping(expression="java(parentEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(sonRepository.countByParentId(parentEntity.getId()))", target = "children" ),
        @Mapping(source = "parentEntity.birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd"),
        @Mapping(source = "parentEntity.age", target = "age"),
        @Mapping(expression="java(parentEntity.getLocale().toString())", target = "locale" )
    })
    @Named("parentEntityToParentDTO")
    public abstract ParentDTO parentEntityToParentDTO(ParentEntity parentEntity); 
	
    @IterableMapping(qualifiedByName = "parentEntityToParentDTO")
    public abstract List<ParentDTO> parentEntitiesToParentDTOs(List<ParentEntity> parentEntities);
    
    @Mappings({ 
		@Mapping(expression="java(passwordEncoder.encode(registerParentDTO.getPasswordClear()))", target = "password"),
		@Mapping(expression="java(authorityRepository.findByType(es.bisite.usal.bulltect.persistence.entity.AuthorityEnum.ROLE_PARENT))", target = "authority"),
        @Mapping(source="registerParentDTO.telephone.rawInput", target = "telephone" )
	})
    public abstract ParentEntity registerParentDTOToParentEntity(RegisterParentDTO registerParentDTO);
    
    @Mappings({ 
		@Mapping(expression="java(passwordEncoder.encode(registerParentByFacebookDTO.getPasswordClear()))", target = "password"),
		@Mapping(expression="java(authorityRepository.findByType(es.bisite.usal.bulltect.persistence.entity.AuthorityEnum.ROLE_PARENT))", target = "authority"),
        @Mapping(source="registerParentByFacebookDTO.telephone.rawInput", target = "telephone" )
	})
    public abstract ParentEntity registerParentByFacebookDTOToParentEntity(RegisterParentByFacebookDTO registerParentByFacebookDTO);
}
