package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.List;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelDTO;

/**
 * App Model Service
 * @author sergiosanchezsanchez
 *
 */
public interface IAppModelService {
	
	/**
	 * Save
	 * @param saveAppModel
	 * @return
	 */
	AppModelDTO save(final SaveAppModelDTO saveAppModel);
	
	/**
	 * Save
	 * @param saveAppModels
	 * @return
	 */
	Iterable<AppModelDTO> save(final List<SaveAppModelDTO> saveAppModels);
	
	

}
