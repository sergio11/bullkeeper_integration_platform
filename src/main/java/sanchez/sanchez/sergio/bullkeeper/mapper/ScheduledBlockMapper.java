package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks.ScheduledBlockSavedSSE;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ScheduledBlockDTO;

/**
 * Scheduled Blocks Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ScheduledBlockMapper {
	
	@Autowired
	protected KidRepository kidRepository;

	
	/**
	 * Scheduled Block Entity To Scheduled Block DTO
	 * @param scheduledBlockEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(scheduledBlockEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(scheduledBlockEntity.getKid().getId().toString())", target = "kid" )
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
	 * Scheduled Block DTO To Scheduled Block SSE
	 * @param scheduledBlockEntity
	 * @return
	 */
    @Mappings({
        @Mapping( target = "subscriberId", ignore = true )
     })
    @Named("scheduledBlockDTOToScheduledBlockSavedSSE")
    public abstract ScheduledBlockSavedSSE scheduledBlockDTOToScheduledBlockSavedSSE(final ScheduledBlockDTO scheduledBlockDTO); 
	
    /**
     * Scheduled Block DTO To Scheduled Block Saved SSE
     * @param scheduledBlockEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "scheduledBlockDTOToScheduledBlockSavedSSE")
    public abstract Iterable<ScheduledBlockSavedSSE> scheduledBlockDTOToScheduledBlockSavedSSE(Iterable<ScheduledBlockDTO> scheduledBlockDTOList);
    
    /**
	 * Save Scheduled Block DTO To Scheduled Block Entity
	 * @param saveScheduledBlockDTO
	 * @return
	 */
    @Mappings({
    	@Mapping(expression="java((saveScheduledBlockDTO.getIdentity() != null && !saveScheduledBlockDTO.getIdentity().isEmpty()) ? new org.bson.types.ObjectId(saveScheduledBlockDTO.getIdentity()) : null )", 
    			target="id"),
        @Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveScheduledBlockDTO.getKid())))", target = "kid" )
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
