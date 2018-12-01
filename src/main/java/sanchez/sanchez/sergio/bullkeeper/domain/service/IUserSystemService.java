package sanchez.sanchez.sergio.bullkeeper.domain.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AdminDTO;

/**
 * User System Service
 * @author sergiosanchezsanchez
 *
 */
public interface IUserSystemService {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	AdminDTO getUserById(String id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	AdminDTO getUserById(ObjectId id);
}
