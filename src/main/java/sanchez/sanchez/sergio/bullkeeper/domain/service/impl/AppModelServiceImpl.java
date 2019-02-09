package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAppModelService;
import sanchez.sanchez.sergio.bullkeeper.mapper.AppModelEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppModelEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppModelRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelDTO;

/**
 * App Model Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service("appModelService")
public final class AppModelServiceImpl implements IAppModelService {
	
	/**
	 * App Model Entity Mapper
	 */
	private final AppModelEntityMapper appModelEntityMapper;
	
	/**
	 * App Model Repository
	 */
	private final AppModelRepository appModelRepository;
	
	
	/**
	 * 
	 * @param appModelEntityMapper
	 * @param appModelRepository
	 */
	@Autowired
	public AppModelServiceImpl(final AppModelEntityMapper appModelEntityMapper, 
			final AppModelRepository appModelRepository) {
		super();
		this.appModelEntityMapper = appModelEntityMapper;
		this.appModelRepository = appModelRepository;
	}

	/**
	 * Save
	 */
	@Override
	public AppModelDTO save(final SaveAppModelDTO saveAppModel) {
		Assert.notNull(saveAppModel, "Save App Model can not be null");
		
		final AppModelEntity appModelToSave = appModelEntityMapper
				.saveAppModelDtoToAppModelEntity(saveAppModel);
		
		final AppModelEntity appModelSaved = appModelRepository.save(appModelToSave);
		
		return appModelEntityMapper.appModelEntityToAppModelDTO(appModelSaved);
	}

	/**
	 * Save
	 */
	@Override
	public Iterable<AppModelDTO> save(final List<SaveAppModelDTO> saveAppModels) {
		Assert.notNull(saveAppModels, "Save App Models can not be null");
		
		final Iterable<AppModelEntity> appModelToSaveList = appModelEntityMapper
				.saveAppModelDtoToAppModelEntity(saveAppModels);
		
		final Iterable<AppModelEntity> appModelSavedList = appModelRepository.save(appModelToSaveList);
		
		return appModelEntityMapper.appModelEntityToAppModelDTOs(appModelSavedList);
	}

}
