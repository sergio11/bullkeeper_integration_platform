package sanchez.sanchez.sergio.masoc.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.masoc.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveScheduledBlockDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.ScheduledBlockDTO;

/**
 * Scheduled Blocks Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ScheduledBlockMapper {
	
	@Autowired
	protected SonRepository sonRepository;

	
	/**
	 * Scheduled Block Entity To Scheduled Block DTO
	 * @param scheduledBlockEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(scheduledBlockEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(scheduledBlockEntity.getSon().getId().toString())", target = "son" )
     })
    @Named("scheduledBlockEntityToScheduledBlockDTO")
    public abstract ScheduledBlockDTO scheduledBlockEntityToScheduledBlockDTO(final ScheduledBlockEntity scheduledBlockEntity); 
	
    /**
     * Scheduled Block Entity To Scheduled Block DTO
     * @param scheduledBlockEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "scheduledBlockEntityToScheduledBlockDTO")
    public abstract Iterable<ScheduledBlockDTO> scheduledBlockEntityToScheduledBlockDTO(Iterable<ScheduledBlockEntity> scheduledBlockEntities);
    
    
    /**
	 * Save Scheduled Block DTO To Scheduled Block Entity
	 * @param saveScheduledBlockDTO
	 * @return
	 */
    @Mappings({
    	@Mapping(expression="java((saveScheduledBlockDTO.getIdentity() != null && !saveScheduledBlockDTO.getIdentity().isEmpty()) ? new org.bson.types.ObjectId(saveScheduledBlockDTO.getIdentity()) : null )", target="id"),
        @Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(saveScheduledBlockDTO.getSon())))", target = "son" )
     })
    @Named("saveScheduledBlockDTOToScheduledBlockEntity")
    public abstract ScheduledBlockEntity saveScheduledBlockDTOToScheduledBlockEntity(final SaveScheduledBlockDTO saveScheduledBlockDTO); 
	
    /**
     * @param saveScheduledBlockEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "saveScheduledBlockDTOToScheduledBlockEntity")
    public abstract Iterable<ScheduledBlockEntity> saveScheduledBlockDTOToScheduledBlockEntity(final Iterable<SaveScheduledBlockDTO> saveScheduledBlockEntities);
    
   
}
