package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppAllowedByScheduledBlockEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppInstalledRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GeofenceRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ScheduledBlockRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
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
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;

	/**
	 * Terminal Repository
	 */
	@Autowired
	protected TerminalRepository terminalRepository;
	
	/**
	 * App Installed Repository
	 */
	@Autowired 
	protected AppInstalledRepository appInstalledRepository;
	
	/**
	 * Geofence Repository
	 */
	@Autowired
	protected GeofenceRepository geofenceRepository;
	
	/**
	 * APp Installed Entity Mapper
	 */
	@Autowired
	protected AppInstalledEntityMapper appInstalledMapper;
	
	/**
	 * Terminal Entity Data Mapper
	 */
	@Autowired
	protected TerminalEntityDataMapper terminalEntityDataMapper;
	
	/**
	 * Geofence Entity Mapper
	 */
	@Autowired
	protected GeofenceEntityMapper geofenceEntityMapper;
	
	/**
	 * Scheduled Block Repository
	 */
	@Autowired
	protected ScheduledBlockRepository scheduledBlockRepository;
	
	
	
	/**
	 * Scheduled Block Entity To Scheduled Block DTO
	 * @param scheduledBlockEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(scheduledBlockEntity.getId().toString())", 
        	target = "identity" ),
        @Mapping(expression="java(scheduledBlockEntity.getKid().getId().toString())",
        	target = "kid" ),
        @Mapping(source = "scheduledBlockEntity.createAt", target = "createAt", 
        	dateFormat = "yyyy/MM/dd"),
        @Mapping(expression="java(appAllowedByScheduledBlockEntityToAppAllowedByScheduledBlockDTO(scheduledBlockEntity.getAppAllowed()))", 
        	target = "appsAllowed"),
        @Mapping(expression="java(geofenceEntityMapper.geofenceEntityToGeofenceDTO(scheduledBlockEntity.getGeofence()))", 
    		target = "geofence")
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
    	@Mapping(expression="java((saveScheduledBlockDTO.getIdentity() != null && !saveScheduledBlockDTO.getIdentity().isEmpty()) ? getScheduledBlockImage(new org.bson.types.ObjectId(saveScheduledBlockDTO.getIdentity())) : null )", 
			target="image"),
        @Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveScheduledBlockDTO.getKid())))", 
        		target = "kid" ),
        @Mapping(expression="java(saveAppAllowedByScheduledBlockDTOToAppAllowedByScheduledBlockEntity(saveScheduledBlockDTO.getAppAllowedList()))", 
        		target = "appAllowed"),
        @Mapping(expression="java((saveScheduledBlockDTO.getGeofence() != null && !saveScheduledBlockDTO.getGeofence().isEmpty()) "
        		+ "? geofenceRepository.findOne(new org.bson.types.ObjectId(saveScheduledBlockDTO.getGeofence())) : null )", 
				target="geofence")
     })
    @Named("saveScheduledBlockDTOToScheduledBlockEntity")
    public abstract ScheduledBlockEntity saveScheduledBlockDTOToScheduledBlockEntity(final SaveScheduledBlockDTO saveScheduledBlockDTO); 
	
    /**
     * @param saveScheduledBlockEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "saveScheduledBlockDTOToScheduledBlockEntity")
    public abstract Iterable<ScheduledBlockEntity> saveScheduledBlockDTOToScheduledBlockEntity(final Iterable<SaveScheduledBlockDTO> saveScheduledBlockEntities);
    
    /**
	 * save App Allowed By Scheduled Block DTO To App Allowed By Scheduled Block Entity
	 * @param saveAppAllowedByScheduledBlock
	 * @return
	 */
    @Mappings({
    	@Mapping(expression="java(appInstalledRepository.findOne(new org.bson.types.ObjectId(saveAppAllowedByScheduledBlock.getApp())))", 
    			target="app"),
        @Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(saveAppAllowedByScheduledBlock.getTerminal())))", 
        target = "terminal" )
     })
    @Named("saveAppAllowedByScheduledBlockDTOToAppAllowedByScheduledBlockEntity")
    public abstract AppAllowedByScheduledBlockEntity saveAppAllowedByScheduledBlockDTOToAppAllowedByScheduledBlockEntity(final SaveScheduledBlockDTO.SaveAppAllowedByScheduledBlockDTO saveAppAllowedByScheduledBlock); 
	
    /**
     * @param saveAppAllowedByScheduledBlockEntitites
     * @return
     */
    @IterableMapping(qualifiedByName = "saveAppAllowedByScheduledBlockDTOToAppAllowedByScheduledBlockEntity")
    public abstract Iterable<AppAllowedByScheduledBlockEntity> saveAppAllowedByScheduledBlockDTOToAppAllowedByScheduledBlockEntity(final Iterable<SaveScheduledBlockDTO.SaveAppAllowedByScheduledBlockDTO> saveAppAllowedByScheduledBlockEntitites);
    
    /**
     * 
     * @param appAllowedByScheduledBlockEntity
     * @return
     */
    @Mappings({
    	@Mapping(expression="java(appInstalledMapper.appInstalledEntityToAppInstalledDTO(appAllowedByScheduledBlockEntity.getApp()))", 
    			target="app"),
        @Mapping(expression="java(terminalEntityDataMapper.terminalEntityToTerminalDetailDTO(appAllowedByScheduledBlockEntity.getTerminal()))", 
        		target = "terminal" )
     })
    @Named("appAllowedByScheduledBlockEntityToAppAllowedByScheduledBlockDTO")
    public abstract ScheduledBlockDTO.AppAllowedByScheduledBlockDTO appAllowedByScheduledBlockEntityToAppAllowedByScheduledBlockDTO(final AppAllowedByScheduledBlockEntity appAllowedByScheduledBlockEntity); 
	
    /**
     * @param appAllowedByScheduledBlockEntitites
     * @return
     */
    @IterableMapping(qualifiedByName = "appAllowedByScheduledBlockEntityToAppAllowedByScheduledBlockDTO")
    public abstract Iterable<ScheduledBlockDTO.AppAllowedByScheduledBlockDTO> appAllowedByScheduledBlockEntityToAppAllowedByScheduledBlockDTO(final Iterable<AppAllowedByScheduledBlockEntity> appAllowedByScheduledBlockEntitites);
    
   /**
    * Get Scheduled Block Image
    * @param id
    */
    protected String getScheduledBlockImage(final ObjectId id) {
    	return scheduledBlockRepository.getImageForScheduledBlock(id);
    }
    
}
