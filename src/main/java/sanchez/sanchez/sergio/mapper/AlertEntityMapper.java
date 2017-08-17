package sanchez.sanchez.sergio.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.dto.request.AddAlertDTO;
import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.dto.response.AlertDTO;
import sanchez.sanchez.sergio.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.repository.SonRepository;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AlertEntityMapper {
	
	@Autowired
	protected SonRepository sonRepository;
	
	@Autowired
	protected SonEntityMapper sonEntityMapper;
    
    @Mappings({
        @Mapping(expression="java(alertEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(sonEntityMapper.sonEntityToSonDTO(alertEntity.getSonEntity()))", target = "son" )
    })
    @Named("alertEntityToSonDTO")
    public abstract AlertDTO alertEntityToAlertDTO(AlertEntity alertEntity); 
	
    @IterableMapping(qualifiedByName = "alertEntityToSonDTO")
    public abstract Iterable<AlertDTO> alertEntitiesToAlertDTO(Iterable<AlertEntity> alertEntities);
    
    @Mappings({ 
		@Mapping(expression="java(sanchez.sanchez.sergio.persistence.entity.AlertLevelEnum.valueOf(addAlertDTO.getLevel()))", target = "level"),
		@Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(addAlertDTO.getTarget())))", target = "son" )
	})
    public abstract AlertEntity addAlertDTOToAlertEntity(AddAlertDTO addAlertDTO);
    
    
}
