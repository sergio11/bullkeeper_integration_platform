package sanchez.sanchez.sergio.masoc.domain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.masoc.domain.service.IScheduledBlockService;
import sanchez.sanchez.sergio.masoc.mapper.ScheduledBlockMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.ScheduledBlockRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveScheduledBlockDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveScheduledBlockStatusDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.ScheduledBlockDTO;

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
	 */
	@Autowired
	public ScheduledBlockServiceImpl(final ScheduledBlockRepository scheduledBlockRepository,
			final ScheduledBlockMapper scheduledBlockMapper) {
		super();
		this.scheduledBlockRepository = scheduledBlockRepository;
		this.scheduledBlockMapper = scheduledBlockMapper;
	}

	/**
	 * Get Scheduled Block By Child
	 */
	@Override
	public Iterable<ScheduledBlockDTO> getScheduledBlockByChild(final ObjectId sonId) {
		Assert.notNull(sonId, "Id can not be null");
		
		// Find By Son ID.
		final Iterable<ScheduledBlockEntity> scheduledBlockEntities = scheduledBlockRepository.findBySonId(sonId);
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
		
		final ScheduledBlockEntity scheduledBlockEntitySaved = 
				scheduledBlockRepository.save(scheduledBlockEntityToSave);
		
		return scheduledBlockMapper.scheduledBlockEntityToScheduledBlockDTO(scheduledBlockEntitySaved);
	}

	/**
	 * Delete By Child ID
	 */
	@Override
	public void deleteByChildId(final ObjectId id) {
		Assert.notNull(id, "Id can not null");
		scheduledBlockRepository.deleteBySonId(id);
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
