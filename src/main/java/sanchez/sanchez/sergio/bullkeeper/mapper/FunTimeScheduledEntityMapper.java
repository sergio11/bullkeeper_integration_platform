package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DayScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DayScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.FunTimeScheduledDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class FunTimeScheduledEntityMapper {
	
   
	/**
	 * 
	 * @param funTimeScheduledEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(dayScheduledEntityToDayScheduledDTO(funTimeScheduledEntity"
        		+ ".getMonday()))", target = "monday" ),
        @Mapping(expression="java(dayScheduledEntityToDayScheduledDTO(funTimeScheduledEntity"
        		+ ".getTuesday()))", target = "tuesday" ),
        @Mapping(expression="java(dayScheduledEntityToDayScheduledDTO(funTimeScheduledEntity"
        		+ ".getWednesday()))", target = "wednesday" ),
        @Mapping(expression="java(dayScheduledEntityToDayScheduledDTO(funTimeScheduledEntity"
        		+ ".getThursday()))", target = "thursday" ),
        @Mapping(expression="java(dayScheduledEntityToDayScheduledDTO(funTimeScheduledEntity"
        		+ ".getFriday()))", target = "friday" ),
        @Mapping(expression="java(dayScheduledEntityToDayScheduledDTO(funTimeScheduledEntity"
        		+ ".getSaturday()))", target = "saturday" ),
        @Mapping(expression="java(dayScheduledEntityToDayScheduledDTO(funTimeScheduledEntity"
        		+ ".getSunday()))", target = "sunday" )
    })
    @Named("funTimeScheduledToFunTimeScheduledDTO")
    public abstract FunTimeScheduledDTO funTimeScheduledToFunTimeScheduledDTO(
    		final FunTimeScheduledEntity funTimeScheduledEntity); 
	
    /**
     * 
     * @param funTimeScheduledEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "funTimeScheduledToFunTimeScheduledDTO")
    public abstract Iterable<FunTimeScheduledDTO> alertEntitiesToAlertDTO(Iterable<FunTimeScheduledEntity> funTimeScheduledEntities);
    
    
    /**
	 * 
	 * @param dayScheduledEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(dayScheduledEntity.getDay().name())", 
        		target = "day" ),
        @Mapping(source = "dayScheduledEntity.pausedAt", target = "pausedAt", 
        	dateFormat = "yyyy/MM/dd HH:mm:ss"),
    })
    @Named("dayScheduledEntityToDayScheduledDTO")
    public abstract DayScheduledDTO dayScheduledEntityToDayScheduledDTO(
    		final DayScheduledEntity dayScheduledEntity); 
	
    
}
