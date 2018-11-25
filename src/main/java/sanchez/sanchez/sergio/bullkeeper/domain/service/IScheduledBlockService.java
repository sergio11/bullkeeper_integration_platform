package sanchez.sanchez.sergio.bullkeeper.domain.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockStatusDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ScheduledBlockDTO;

/**
 * Scheduled Block Service
 * @author sergiosanchezsanchez
 */
public interface IScheduledBlockService {
	
	/**
	 * Get Sheduled Block By Child
	 * @param id
	 * @return
	 */
	Iterable<ScheduledBlockDTO> getScheduledBlockByChild(final ObjectId id);
	
	/**
	 * Save
	 * @param saveScheduledBlockDTO
	 * @return
	 */
	ScheduledBlockDTO save(final SaveScheduledBlockDTO saveScheduledBlockDTO);
	
	/**
	 * Delete By Child ID
	 * @param id
	 */
	void deleteByKidId(final ObjectId id);
	
	/**
	 * Delete by id
	 * @param id
	 */
	void delete(final ObjectId id);
	
	/**
	 * Get Scheduled Block By ID
	 * @param id
	 * @return
	 */
	ScheduledBlockDTO getScheduledBlockById(final ObjectId id);
	
	
	/**
	 * Save Status
	 * @param scheduledStatus
	 * @return
	 */
	void saveStatus(final Iterable<SaveScheduledBlockStatusDTO> scheduledStatus);
	
	
	
	
}
