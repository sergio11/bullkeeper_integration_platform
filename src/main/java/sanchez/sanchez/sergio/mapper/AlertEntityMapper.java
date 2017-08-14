package sanchez.sanchez.sergio.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.dto.response.AlertDTO;
import sanchez.sanchez.sergio.persistence.entity.AlertEntity;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AlertEntityMapper {
	
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
}
