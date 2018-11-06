package sanchez.sanchez.sergio.masoc.domain.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.masoc.domain.service.IScheduledBlockService;
import sanchez.sanchez.sergio.masoc.mapper.ScheduledBlockMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.IScheduledBlockRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveScheduledBlockDTO;
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
	private final IScheduledBlockRepository scheduledBlockRepository;
	
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
	public ScheduledBlockServiceImpl(final IScheduledBlockRepository scheduledBlockRepository,
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

}
