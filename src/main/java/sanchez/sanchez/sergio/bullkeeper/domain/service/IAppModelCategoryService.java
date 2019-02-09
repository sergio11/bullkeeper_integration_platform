package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.List;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelCategoryDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelCategoryDTO;

/**
 * App Model Category Service
 * @author sergiosanchezsanchez
 *
 */
public interface IAppModelCategoryService {
	
	/**
	 * Save
	 * @param saveAppModelCategory
	 * @return
	 */
	AppModelCategoryDTO save(final SaveAppModelCategoryDTO saveAppModelCategory);
	
	/**
	 * Save
	 * @param saveAppModelCategoryList
	 * @return
	 */
	Iterable<AppModelCategoryDTO> save(final List<SaveAppModelCategoryDTO> saveAppModelCategoryList);


}
