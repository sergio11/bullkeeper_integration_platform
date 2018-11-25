package sanchez.sanchez.sergio.bullkeeper.domain.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;

import org.bson.types.ObjectId;


/**
 * Kid Service
 * @author sergiosanchezsanchez
 *
 */
public interface IKidService {
	
	/**
	 *
	 * @param page
	 * @param size
	 * @return
	 */
    Page<KidDTO> findPaginated(Integer page, Integer size);
    
    /**
     * 
     * @param pageable
     * @return
     */
    Page<KidDTO> findPaginated(Pageable pageable);
    
    
    /**
     * 
     * @param id
     * @return
     */
    KidDTO getKidById(final String id);
    
    /**
     * 
     * @return
     */
    Long getTotalKids();
    
    /**
     * 
     * @param id
     * @return
     */
    String getProfileImage(ObjectId id);
    
    /**
     * 
     * @param id
     */
    void deleteById(String id);
    
    /**
     * 
     * @param id
     */
    void deleteById(ObjectId id);
   
}
