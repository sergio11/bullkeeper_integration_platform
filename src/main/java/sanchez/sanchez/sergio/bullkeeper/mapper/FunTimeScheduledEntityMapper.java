package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DayScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveDayScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveFunTimeScheduledDTO;
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
    @IterableMapping(qualifiedByName = "funTimeScheduledEntityToFunTimeScheduledDTO")
    public abstract Iterable<FunTimeScheduledDTO> funTimeScheduledEntityToFunTimeScheduledDTO(Iterable<FunTimeScheduledEntity> funTimeScheduledEntities);
   
    

	/**
	 * 
	 * @param funTimeScheduledEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(saveDayScheduledDtoToDayScheduledEntity(saveFunTimeScheduledDTO"
        		+ ".getMonday()))", target = "monday" ),
        @Mapping(expression="java(saveDayScheduledDtoToDayScheduledEntity(saveFunTimeScheduledDTO"
        		+ ".getTuesday()))", target = "tuesday" ),
        @Mapping(expression="java(saveDayScheduledDtoToDayScheduledEntity(saveFunTimeScheduledDTO"
        		+ ".getWednesday()))", target = "wednesday" ),
        @Mapping(expression="java(saveDayScheduledDtoToDayScheduledEntity(saveFunTimeScheduledDTO"
        		+ ".getThursday()))", target = "thursday" ),
        @Mapping(expression="java(saveDayScheduledDtoToDayScheduledEntity(saveFunTimeScheduledDTO"
        		+ ".getFriday()))", target = "friday" ),
        @Mapping(expression="java(saveDayScheduledDtoToDayScheduledEntity(saveFunTimeScheduledDTO"
        		+ ".getSaturday()))", target = "saturday" ),
        @Mapping(expression="java(saveDayScheduledDtoToDayScheduledEntity(saveFunTimeScheduledDTO"
        		+ ".getSunday()))", target = "sunday" )
    })
    @Named("saveFunTimeScheduledDtoToFunTimeScheduledEntity")
    public abstract FunTimeScheduledEntity saveFunTimeScheduledDtoToFunTimeScheduledEntity(
    		final SaveFunTimeScheduledDTO saveFunTimeScheduledDTO); 
	
    /**
     * 
     * @param saveFunTimeScheduledDTOList
     * @return
     */
    @IterableMapping(qualifiedByName = "saveFunTimeScheduledDtoToFunTimeScheduledEntity")
    public abstract Iterable<FunTimeScheduledDTO> saveFunTimeScheduledDTOToFunTimeScheduledEntity(Iterable<SaveFunTimeScheduledDTO> saveFunTimeScheduledDTOList);
    
    
    /**
	 * 
	 * @param dayScheduledEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(dayScheduledEntity.getDay().name())", 
        		target = "day" )
    })
    @Named("dayScheduledEntityToDayScheduledDTO")
    public abstract DayScheduledDTO dayScheduledEntityToDayScheduledDTO(
    		final DayScheduledEntity dayScheduledEntity); 
    
    
    
    /**
	 * Day Scheduled Entity To Save Day Scheduled DTO
	 * @param saveDayScheduledDTO
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeDaysEnum.valueOf(saveDayScheduledDTO.getDay()))", 
        		target = "day" )
    })
    @Named("saveDayScheduledDtoToDayScheduledEntity")
    public abstract DayScheduledEntity saveDayScheduledDtoToDayScheduledEntity(
    		final SaveDayScheduledDTO saveDayScheduledDTO); 
	
    
}
