package sanchez.sanchez.sergio.masoc.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.AddAlertDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.AlertDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AlertEntityMapper {
	
	@Autowired
	protected SonRepository sonRepository;
	
	@Autowired
	protected SonEntityMapper sonEntityMapper;
	
	@Autowired
	protected PrettyTime prettyTime;
    
    @Mappings({
        @Mapping(expression="java(alertEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(sonEntityMapper.sonEntityToSonDTO(alertEntity.getSon()))", target = "son" ),
        @Mapping(source = "alertEntity.createAt", target = "createAt", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(expression="java(prettyTime.format(alertEntity.getCreateAt()))", target = "since"),
        @Mapping(expression="java(alertEntity.getCategory().name())", target = "category" )
    })
    @Named("alertEntityToSonDTO")
    public abstract AlertDTO alertEntityToAlertDTO(AlertEntity alertEntity); 
	
    @IterableMapping(qualifiedByName = "alertEntityToSonDTO")
    public abstract Iterable<AlertDTO> alertEntitiesToAlertDTO(Iterable<AlertEntity> alertEntities);
    
    @Mappings({ 
		@Mapping(expression="java(sanchez.sanchez.sergio.masoc.persistence.entity.AlertLevelEnum.valueOf(addAlertDTO.getLevel()))", target = "level"),
		@Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(addAlertDTO.getTarget())))", target = "son" )
	})
    public abstract AlertEntity addAlertDTOToAlertEntity(AddAlertDTO addAlertDTO);
    
    
}
