package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddAlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AlertEntityMapper {
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Kid Entity Mapper
	 */
	@Autowired
	protected KidEntityMapper kidEntityMapper;
	
	/**
	 * Pretty Time
	 */
	@Autowired
	protected PrettyTime prettyTime;
    
	/**
	 * 
	 * @param alertEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(alertEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(kidEntityMapper.kidEntityToKidDTO(alertEntity.getKid()))", target = "kid" ),
        @Mapping(source = "alertEntity.createAt", target = "createAt", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(expression="java(prettyTime.format(alertEntity.getCreateAt()))", target = "since"),
        @Mapping(expression="java(alertEntity.getCategory().name())", target = "category" )
    })
    @Named("alertEntityToSonDTO")
    public abstract AlertDTO alertEntityToAlertDTO(AlertEntity alertEntity); 
	
    /**
     * 
     * @param alertEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "alertEntityToSonDTO")
    public abstract Iterable<AlertDTO> alertEntitiesToAlertDTO(Iterable<AlertEntity> alertEntities);
    
    /**
     * 
     * @param addAlertDTO
     * @return
     */
    @Mappings({ 
		@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum.valueOf(addAlertDTO.getLevel()))", target = "level"),
		@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(addAlertDTO.getTarget())))", target = "kid" )
	})
    public abstract AlertEntity addAlertDTOToAlertEntity(AddAlertDTO addAlertDTO);
    
    
}
