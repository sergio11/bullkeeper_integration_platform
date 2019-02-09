package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAppModelCategoryService;
import sanchez.sanchez.sergio.bullkeeper.mapper.AppModelCategoryEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppModelCategoryEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppModelCategoryRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelCategoryDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelCategoryDTO;

/**
 * App Model Category Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service("appModelCategoryService")
public final class AppModelCategoryServiceImpl implements IAppModelCategoryService {

	/**
	 * App Model Category Repository
	 */
	private final AppModelCategoryRepository appModelCategoryRepository;
	
	/**
	 * App Model Category Entity Mapper
	 */
	private final AppModelCategoryEntityMapper appModelCategoryEntityMapper;
	
	
	/**
	 * 
	 * @param appModelCategoryRepository
	 * @param appModelCategoryEntityMapper
	 */
	public AppModelCategoryServiceImpl(final AppModelCategoryRepository appModelCategoryRepository,
			AppModelCategoryEntityMapper appModelCategoryEntityMapper) {
		super();
		this.appModelCategoryRepository = appModelCategoryRepository;
		this.appModelCategoryEntityMapper = appModelCategoryEntityMapper;
	}

	/**
	 * Save
	 */
	@Override
	public AppModelCategoryDTO save(final SaveAppModelCategoryDTO saveAppModelCategoryDTO) {
		Assert.notNull(saveAppModelCategoryDTO, "Save App Model categoy can not be null");
		
		final AppModelCategoryEntity appModelCategoryToSave = 
				appModelCategoryEntityMapper.saveAppModelCategoryDtoToAppModelCategoryEntity(saveAppModelCategoryDTO);
		
		final AppModelCategoryEntity appModelCategorySaved = 
				appModelCategoryRepository.save(appModelCategoryToSave);
		
		return appModelCategoryEntityMapper
				.appModelCategoryEntityToAppModelCategoryDTO(appModelCategorySaved);
	}

	/**
	 * Save
	 */
	@Override
	public Iterable<AppModelCategoryDTO> save(final List<SaveAppModelCategoryDTO> saveAppModelCategoryList) {
		Assert.notNull(saveAppModelCategoryList, "Save App Model categoy can not be null");

		final List<AppModelCategoryDTO> appModelCategorieList = new ArrayList<>();
		
		for(final SaveAppModelCategoryDTO saveAppModelCategoryDTO: saveAppModelCategoryList)
			appModelCategorieList.add(save(saveAppModelCategoryDTO));
			
		return appModelCategorieList;
		
	}

}
