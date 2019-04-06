package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Iterables;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IScheduledBlockService;
import sanchez.sanchez.sergio.bullkeeper.exception.ScheduledBlockNotValidException;
import sanchez.sanchez.sergio.bullkeeper.mapper.ScheduledBlockMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ScheduledBlockRepository;
import sanchez.sanchez.sergio.bullkeeper.util.Utils;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockStatusDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;

/**
 * Scheduled Block Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class ScheduledBlockServiceImpl implements IScheduledBlockService {
	
	private Logger logger = LoggerFactory.getLogger(ScheduledBlockServiceImpl.class);

	/**
	 * Scheduled Block Repository
	 */
	private final ScheduledBlockRepository scheduledBlockRepository;
	
	/**
	 * Scheduled Block Mapper
	 */
	private final ScheduledBlockMapper scheduledBlockMapper;
	
	/**
	 * Upload File Service
	 */
	private final IUploadFilesService uploadFilesService;

	/**
	 * 
	 * @param scheduledBlockRepository
	 * @param scheduledBlockMapper
	 * @param uploadFilesService
	 */
	@Autowired
	public ScheduledBlockServiceImpl(
			final ScheduledBlockRepository scheduledBlockRepository,
			final ScheduledBlockMapper scheduledBlockMapper,
			final IUploadFilesService uploadFilesService) {
		super();
		this.scheduledBlockRepository = scheduledBlockRepository;
		this.scheduledBlockMapper = scheduledBlockMapper;
		this.uploadFilesService = uploadFilesService;
	}

	/**
	 * Get Scheduled Block By Child
	 * @param kid
	 */
	@Override
	public Iterable<ScheduledBlockDTO> getScheduledBlockByChild(final ObjectId kid) {
		Assert.notNull(kid, "Id can not be null");
		
		// Find By Son ID.
		final Iterable<ScheduledBlockEntity> scheduledBlockEntities = scheduledBlockRepository.findByKidId(kid);
		return scheduledBlockMapper.scheduledBlockEntityToScheduledBlockDTO(scheduledBlockEntities);
	}

	
	/**
	 * Save Scheduled Block
	 * @param saveScheduledBlockDTO
	 */
	@Override
	public ScheduledBlockDTO save(final SaveScheduledBlockDTO saveScheduledBlockDTO) {
		Assert.notNull(saveScheduledBlockDTO, "Save Scheduled Block DTO");
		
		final ScheduledBlockEntity scheduledBlockEntityToSave = 
				scheduledBlockMapper.saveScheduledBlockDTOToScheduledBlockEntity(saveScheduledBlockDTO);
	
		
		logger.debug("Scheduled Block Image After Mapping -> " + scheduledBlockEntityToSave.getImage());
		
		boolean canBeSaved = true;
		
		if(scheduledBlockEntityToSave.getStartAt()
				.isBefore(scheduledBlockEntityToSave.getEndAt())) {
			
			
			// Get all blocks configured for the child
			final Iterable<ScheduledBlockEntity> scheduledBlocksConfigured = 
					scheduledBlockEntityToSave.getId() != null  ? 
							scheduledBlockRepository.findByIdNotAndKidId(scheduledBlockEntityToSave.getId(), 
									scheduledBlockEntityToSave.getKid().getId()):
					scheduledBlockRepository.findByKidId(scheduledBlockEntityToSave.getKid().getId());
			
			
			
			if(Iterables.size(scheduledBlocksConfigured) > 0) {
				
				for(final ScheduledBlockEntity scheduledBlockConfigured: scheduledBlocksConfigured) {
				
					boolean matchSomeDayOfWeek = false;
					
					
					// Check Weekly Frequency
					if(scheduledBlockEntityToSave.getWeeklyFrequency().length == 
							scheduledBlockConfigured.getWeeklyFrequency().length) {
						int weeklyFrequencyLength = scheduledBlockEntityToSave.getWeeklyFrequency().length;
						for(int i = 0; i < weeklyFrequencyLength; i++) {
							if((scheduledBlockEntityToSave.getWeeklyFrequency()[i] == 1 && 
									scheduledBlockConfigured.getWeeklyFrequency()[i] == 1) && 
									(scheduledBlockConfigured.isRepeatable() 
											|| Utils.withinTheSameWeek(scheduledBlockConfigured.getCreateAt(),
													scheduledBlockEntityToSave.getCreateAt()) )) {
								matchSomeDayOfWeek = true;
								break;
							}
							
						}
					}
					
					
					if(matchSomeDayOfWeek)
						if(!(scheduledBlockEntityToSave.getStartAt()
								.isBefore(scheduledBlockConfigured.getStartAt()) && 
							scheduledBlockEntityToSave.getEndAt()
								.isBefore(scheduledBlockConfigured.getStartAt())) ||
							
							(scheduledBlockEntityToSave.getStartAt()
									.isAfter(scheduledBlockConfigured.getEndAt()) && 
								scheduledBlockEntityToSave.getEndAt()
									.isAfter(scheduledBlockConfigured.getEndAt()))) {
							canBeSaved = false;
							break;
						}
				}
				
			}
			
		
		} else {
			canBeSaved = false;
		}
		
		
		if(!canBeSaved) 
			throw new ScheduledBlockNotValidException();
		
		logger.debug("Scheduled Block Image Before Save-> " + scheduledBlockEntityToSave.getImage());
		
		final ScheduledBlockEntity scheduledBlockEntitySaved = 
				scheduledBlockRepository.save(scheduledBlockEntityToSave);
		
		logger.debug("Scheduled Block Image After Save-> " + scheduledBlockEntityToSave.getImage());
		
		return scheduledBlockMapper.scheduledBlockEntityToScheduledBlockDTO(scheduledBlockEntitySaved);
	}

	/**
	 * Delete By Child ID
	 * @param id
	 */
	@Override
	public void deleteByKidId(final ObjectId id) {
		Assert.notNull(id, "Id can not null");
		scheduledBlockRepository.deleteByKidId(id);
	}

	/**
	 * Delete By ID
	 * @param id
	 */
	@Override
	public void delete(final ObjectId id) {
		Assert.notNull(id, "Id can not null");
		
		scheduledBlockRepository.delete(id);
	
	}

	/**
	 * Get Scheduled Block By ID
	 * 
	 * @parma id
	 */
	@Override
	public ScheduledBlockDTO getScheduledBlockById(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		// Find Scheduled Block
		final ScheduledBlockEntity scheduledBlockEntity = 
				scheduledBlockRepository.findOne(id);
		
		// Map Scheduled Entity to Scheduled Block DTO
		final ScheduledBlockDTO scheduledBlockDTO = 
				scheduledBlockMapper.scheduledBlockEntityToScheduledBlockDTO(scheduledBlockEntity);
		
		return scheduledBlockDTO;
	}

	/**
	 * Save Status
	 * 
	 * @param scheduledStatus
	 */
	@Override
	public void saveStatus(Iterable<SaveScheduledBlockStatusDTO> scheduledStatus) {
		Assert.notNull(scheduledStatus, "Scheduled Status can not be null");
		
		final List<ObjectId> scheduledBlocksToEnable = new ArrayList<ObjectId>(), 
				scheduledBlockToDisable = new ArrayList<ObjectId>();
		
		for(final SaveScheduledBlockStatusDTO saveScheduled: scheduledStatus) {
			
			final ObjectId scheduledBlockId = new ObjectId(saveScheduled.getIdentity());
			
			if(saveScheduled.isEnable())
				scheduledBlocksToEnable.add(scheduledBlockId);
			else
				scheduledBlockToDisable.add(scheduledBlockId);
		
		}
		
		if(!scheduledBlocksToEnable.isEmpty())
			scheduledBlockRepository.enableScheduledBlocks(scheduledBlocksToEnable);
		
		if(!scheduledBlockToDisable.isEmpty())
			scheduledBlockRepository.disableScheduledBlocks(scheduledBlockToDisable);
		
	}
	
	
	/**
     * Upload Scheduled Block Image
     * @param childId
     * @param blockId
     * @param requestUploadFile
     */
    @Override
	public ImageDTO uploadScheduledBlockImage(final ObjectId childId, final ObjectId blockId,
			final RequestUploadFile requestUploadFile) {
    	Assert.notNull(childId, "Child Id can not be null");
        Assert.notNull(blockId, "Block Id can not be null");
        Assert.notNull(requestUploadFile, "Request Upload File can not be null");
        
        // Get Scheduled Block
        final ScheduledBlockEntity scheduledBlockEntity = 
        		scheduledBlockRepository.findByIdAndKidId(blockId, childId);
        
        // Save File
        final String scheduledBlockImage = uploadFilesService.save(requestUploadFile);
        
        if(scheduledBlockEntity.getImage() != null)
        	uploadFilesService.delete(scheduledBlockEntity.getImage());
        
        scheduledBlockEntity.setImage(scheduledBlockImage);
        
        scheduledBlockRepository.save(scheduledBlockEntity);
        
        return uploadFilesService.getImage(scheduledBlockImage);
     
	}
    
    /**
     * Get Scheduled Block Image
     * @param childId
     * @param blockId
     */
    @Override
	public UploadFileInfo getScheduledBlockImage(final ObjectId childId, final ObjectId blockId) {
    	Assert.notNull(childId, "Child Id can not be null");
        Assert.notNull(blockId, "Block Id can not be null");
        
        // Get Scheduled Block
        final ScheduledBlockEntity scheduledBlockEntity = 
        		scheduledBlockRepository.findByIdAndKidId(blockId, childId);
         
        // Get Upload file info
        return uploadFilesService.getFileInfo(scheduledBlockEntity.getImage());
       
	}

}
