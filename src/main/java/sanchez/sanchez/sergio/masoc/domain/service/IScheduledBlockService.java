package sanchez.sanchez.sergio.masoc.domain.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.masoc.web.dto.request.SaveScheduledBlockDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.ScheduledBlockDTO;

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
	void deleteByChildId(final ObjectId id);
	
	/**
	 * Delete by id
	 * @param id
	 */
	void delete(final ObjectId id);
}
