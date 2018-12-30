package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
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
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ScheduledBlockDTO;

/**
 * Scheduled Block Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class ScheduledBlockServiceImpl implements IScheduledBlockService {

	/**
	 * Scheduled Block Repository
	 */
	private final ScheduledBlockRepository scheduledBlockRepository;
	
	/**
	 * Scheduled Block Mapper
	 */
	private final ScheduledBlockMapper scheduledBlockMapper;

	
	/**
	 * 
	 * @param scheduledBlockRepository
	 * @param scheduledBlockMapper
	 * @param sseService
	 */
	@Autowired
	public ScheduledBlockServiceImpl(
			final ScheduledBlockRepository scheduledBlockRepository,
			final ScheduledBlockMapper scheduledBlockMapper) {
		super();
		this.scheduledBlockRepository = scheduledBlockRepository;
		this.scheduledBlockMapper = scheduledBlockMapper;
	}

	/**
	 * Get Scheduled Block By Child
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
	 */
	@Override
	public ScheduledBlockDTO save(final SaveScheduledBlockDTO saveScheduledBlockDTO) {
		Assert.notNull(saveScheduledBlockDTO, "Save Scheduled Block DTO");
		
		final ScheduledBlockEntity scheduledBlockEntityToSave = 
				scheduledBlockMapper.saveScheduledBlockDTOToScheduledBlockEntity(saveScheduledBlockDTO);
	
		boolean canBeSaved = true;
		
		
		if(scheduledBlockEntityToSave.getStartAt().isBefore(scheduledBlockEntityToSave.getEndAt())) {
			
			
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
		
		final ScheduledBlockEntity scheduledBlockEntitySaved = 
				scheduledBlockRepository.save(scheduledBlockEntityToSave);
		
		
		return scheduledBlockMapper.scheduledBlockEntityToScheduledBlockDTO(scheduledBlockEntitySaved);
	}

	/**
	 * Delete By Child ID
	 */
	@Override
	public void deleteByKidId(final ObjectId id) {
		Assert.notNull(id, "Id can not null");
		scheduledBlockRepository.deleteByKidId(id);
	}

	/**
	 * Delete By ID
	 */
	@Override
	public void delete(final ObjectId id) {
		Assert.notNull(id, "Id can not null");
		
		scheduledBlockRepository.delete(id);
	
	}

	/**
	 * Get Scheduled Block By ID
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

}
