package sanchez.sanchez.sergio.masoc.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.masoc.web.dto.request.AddSchoolDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SchoolNameDTO;

/**
 * School Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface ISchoolService {
	
	/**
	 * Find Paginated
	 * @param page
	 * @param size
	 * @return
	 */
	Page<SchoolDTO> findPaginated(final Integer page, final Integer size);
	
	/**
	 * Find Paginated
	 * @param pageable
	 * @return
	 */
    Page<SchoolDTO> findPaginated(final Pageable pageable);
    
    /**
     * Find By Name Paginated
     * @param name
     * @param pageable
     * @return
     */
    Page<SchoolDTO> findByNamePaginated(final String name, final Pageable pageable);
    
    /**
     * Find By Name
     * @param name
     * @return
     */
    Iterable<SchoolDTO> findByName(final String name);
    
    /**
     * Get All School Names
     * @return
     */
    Iterable<SchoolNameDTO> getAllSchoolNames();
    
    /**
     * Get Total Number Of Schools
     * @return
     */
    Long getTotalNumberOfSchools();
    
    /**
     * Get School By Id
     * @param id
     * @return
     */
    SchoolDTO getSchoolById(String id);
    
    /**
     * Save
     * @param addSchoolDTO
     * @return
     */
    SchoolDTO save(AddSchoolDTO addSchoolDTO);
    
    /**
     * Delete
     * @param id
     * @return
     */
    SchoolDTO delete(String id);
}
