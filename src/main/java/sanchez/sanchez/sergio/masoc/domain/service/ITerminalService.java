package sanchez.sanchez.sergio.masoc.domain.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.masoc.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.CommentDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.TerminalDTO;

/**
 * Terminal Service
 * @author sergiosanchezsanchez
 *
 */
public interface ITerminalService {
	
	/**
	 * 
	 * @param saveTerminalDTO
	 * @return
	 */
	TerminalDTO save(final SaveTerminalDTO saveTerminalDTO);
	
	/**
     * Get Terminals By Child Id
     * @param userId
     * @return
     */
    Iterable<TerminalDTO> getTerminalsByChildId(final String childId);
    
    /**
     * Delete By Id
     * @param id
     */
    void deleteById(final ObjectId id);

}
