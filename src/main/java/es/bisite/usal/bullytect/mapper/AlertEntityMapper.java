package es.bisite.usal.bullytect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bullytect.dto.request.AddAlertDTO;
import es.bisite.usal.bullytect.dto.response.AlertDTO;
import es.bisite.usal.bullytect.persistence.entity.AlertEntity;
import es.bisite.usal.bullytect.persistence.repository.SonRepository;

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
        @Mapping(expression="java(sonEntityMapper.sonEntityToSonDTO(alertEntity.getSon()))", target = "son" )
    })
    @Named("alertEntityToSonDTO")
    public abstract AlertDTO alertEntityToAlertDTO(AlertEntity alertEntity); 
	
    @IterableMapping(qualifiedByName = "alertEntityToSonDTO")
    public abstract Iterable<AlertDTO> alertEntitiesToAlertDTO(Iterable<AlertEntity> alertEntities);
    
    @Mappings({ 
		@Mapping(expression="java(es.bisite.usal.bullytect.persistence.entity.AlertLevelEnum.valueOf(addAlertDTO.getLevel()))", target = "level"),
		@Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(addAlertDTO.getTarget())))", target = "son" )
	})
    public abstract AlertEntity addAlertDTOToAlertEntity(AddAlertDTO addAlertDTO);
    
    
}
