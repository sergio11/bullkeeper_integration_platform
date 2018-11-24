package sanchez.sanchez.sergio.masoc.domain.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.masoc.domain.service.ITerminalService;
import sanchez.sanchez.sergio.masoc.exception.AppInstalledNotFoundException;
import sanchez.sanchez.sergio.masoc.mapper.AppInstalledEntityMapper;
import sanchez.sanchez.sergio.masoc.mapper.TerminalEntityDataMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.AppInstalledEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.AppRuleEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.AppInstalledRepository;
import sanchez.sanchez.sergio.masoc.persistence.repository.ITerminalRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveAppRulesDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.AppInstalledDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.TerminalDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.TerminalDetailDTO;

/**
 * Terminal Service
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class TerminalServiceImpl implements ITerminalService {
	
	private Logger logger = LoggerFactory.getLogger(TerminalServiceImpl.class);

	/**
	 * Terminal Entity Data Mapper
	 */
	private final TerminalEntityDataMapper terminalEntityDataMapper;
	
	/**
	 * Terminal Repository
	 */
	private final ITerminalRepository terminalRepository;
	
	/**
	 * App Installed Repository
	 */
	private final AppInstalledRepository appsInstalledRepository;
	
	/**
	 * App Installed Entity data mapper
	 */
	private final AppInstalledEntityMapper appInstalledEntityDataMapper;
	

	/**
	 * 
	 * @param terminalEntityDataMapper
	 * @param terminalRepository
	 * @param appsInstalledRepository
	 * @param appInstalledEntityDataMapper
	 */
	@Autowired
	public TerminalServiceImpl(final TerminalEntityDataMapper terminalEntityDataMapper, 
			final ITerminalRepository terminalRepository, final AppInstalledRepository appsInstalledRepository,
			final AppInstalledEntityMapper appInstalledEntityDataMapper) {
		super();
		this.terminalEntityDataMapper = terminalEntityDataMapper;
		this.terminalRepository = terminalRepository;
		this.appsInstalledRepository = appsInstalledRepository;
		this.appInstalledEntityDataMapper = appInstalledEntityDataMapper;
	}

	/**
	 * Save
	 */
	@Override
	public TerminalDTO save(final SaveTerminalDTO saveTerminalDTO) {
		Assert.notNull(saveTerminalDTO, "Save Terminal can not be null");
		
		logger.debug("Save Terminal");
		// Map DTO to Entity
		final TerminalEntity terminalEntityToSave = 
				terminalEntityDataMapper.saveTerminalDtoToTerminalEntity(saveTerminalDTO);
        // Save Terminal Entity
		final TerminalEntity terminalEntitySaved = 
				terminalRepository.save(terminalEntityToSave);
	
        logger.debug("Terminal Entity Saved -> " + terminalEntitySaved.toString());
        
        return terminalEntityDataMapper.terminalEntityToTerminalDTO(terminalEntitySaved);
		
	}

	/**
	 * Get Terminales By Child ID
	 */
	@Override
	public Iterable<TerminalDTO> getTerminalsByChildId(final String childId) {
		Assert.notNull(childId, "Child Id can not be null");
		Assert.hasLength(childId, "Child id can not be empty");
		
		final List<TerminalEntity> terminalEntities = 
				terminalRepository.findBySonEntityId(new ObjectId(childId));
		
		return terminalEntityDataMapper
				.terminalEntityToTerminalDTO(terminalEntities);
		
	}

	/**
	 * Delete By Id
	 */
	@Override
	public void deleteById(ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		terminalRepository.delete(id);
	}

	/**
	 * Get All apps installed in the terminal
	 */
	@Override
	public Iterable<AppInstalledDTO> getAllAppsInstalledInTheTerminal(final ObjectId sonId, final ObjectId terminalId) {
		Assert.notNull(sonId, "Son Id can not be null");
		Assert.notNull(terminalId, "Terminal Id can not be null");
		
		// Find All Apps installed by terminal and son
		final Iterable<AppInstalledEntity> appInstalled = 
				appsInstalledRepository.findAllByTerminalIdAndSonId(terminalId, sonId);
		
		return appInstalledEntityDataMapper.appInstalledEntityToAppInstalledDTO(appInstalled);
	}

	/**
	 * Save App Installed DTO
	 */
	@Override
	public AppInstalledDTO save(SaveAppInstalledDTO saveAppInstalledDTO) {
		Assert.notNull(saveAppInstalledDTO, "Save App Installed can not be null");
		
		// Map to AppInstalled Entity
		final AppInstalledEntity appIstalledToSave = null;
		// Save App Installed
		final AppInstalledEntity appInstalledSave = 
				appsInstalledRepository.save(appIstalledToSave);
		
		return appInstalledEntityDataMapper.appInstalledEntityToAppInstalledDTO(appInstalledSave);
	}

	/**
	 * Save
	 */
	@Override
	public Iterable<AppInstalledDTO> save(Iterable<SaveAppInstalledDTO> saveAppsInstalledDTO) {
		Assert.notNull(saveAppsInstalledDTO, "Apps installed can not be null");
		
		final Iterable<AppInstalledEntity> appsInstalledToSave = 
				appInstalledEntityDataMapper.saveAppInstalledDtoToAppInstalledEntity(saveAppsInstalledDTO);
		
		// Save apps installed list
		final Iterable<AppInstalledEntity> appsInstalledSaved = 
				appsInstalledRepository.save(appsInstalledToSave);
		
		return appInstalledEntityDataMapper.appInstalledEntityToAppInstalledDTO(appsInstalledSaved);
	
	}

	/**
	 * Delete Apps installed by child id and terminal id
	 */
	@Override
	public void deleteAppsInstalledByChildIdAndTerminalId(final ObjectId childId, final ObjectId terminalId) {
		Assert.notNull(childId, "Child id can not be null");
		Assert.notNull(terminalId, "Terminal id can not be null");
		
		appsInstalledRepository.deleteBySonIdAndTerminalId(childId, terminalId);
	}

	/**
	 * Delete app installed by child id
	 */
	@Override
	public void deleteAppInstalledById(final ObjectId appId) throws Throwable {
		Assert.notNull(appId, "App id can not be null");
		
		// Find By Id
		final AppInstalledEntity appInstalled = Optional.ofNullable(appsInstalledRepository.findById(appId))
			.orElseThrow(() -> { throw new AppInstalledNotFoundException(); });
		
		// Deleted
		appsInstalledRepository.delete(appInstalled);
		
	}

	/**
	 * Get Terminal By Id And Child Id
	 */
	@Override
	public TerminalDTO getTerminalByIdAndChildId(final ObjectId terminalId, final ObjectId childId) {
		Assert.notNull(terminalId, "Terminal id can not be null");
		Assert.notNull(childId, "Child id can not be null");
		
		// Get terminal
		final TerminalEntity terminalEntity = terminalRepository.findByIdAndSonEntityId(terminalId, childId);
	
		/**
		 * Terminal Entity to terminal DTO
		 */
		return terminalEntityDataMapper.terminalEntityToTerminalDTO(terminalEntity);
	}

	/**
	 * Save App Rules
	 */
	@Override
	public void saveAppRules(Iterable<SaveAppRulesDTO> appRulesList) {
		Assert.notNull(appRulesList, "App Rules can not be null");
		
		final Map<ObjectId, AppRuleEnum> appRulesMap = new HashMap<>();
		for(final SaveAppRulesDTO appRule: appRulesList)
			appRulesMap.put(new ObjectId(appRule.getIdentity()), 
					AppRuleEnum.valueOf(appRule.getType()));
		
		// Update App Rules
		appsInstalledRepository.updateAppRules(appRulesMap);
		
	}

	
	/**
	 * Get Terminal Detail BY Child Id And Terminal Id
	 */
	@Override
	public TerminalDetailDTO getTerminalDetail(final ObjectId childId, 
			final String terminalId) {
		Assert.notNull(childId, "Child id can not be null");
		Assert.notNull(terminalId, "Terminal Id can not be null");
		
		
		// Find By Id And Son Entity
		final TerminalEntity terminalEntity = ObjectId.isValid(terminalId) ?
				terminalRepository.findByIdAndSonEntityId(new ObjectId(terminalId), childId):
					terminalRepository.findByDeviceIdAndSonEntityId(terminalId, childId);
					
		
		// Transform to Terminal Detail
		return terminalEntityDataMapper.terminalEntityToTerminalDetailDTO(terminalEntity);
		
	}

	
	/**
	 * Get Count Apps Installed In The Terminal
	 */
	@Override
	public long getCountAppsInstalledInTheTerminal(ObjectId sonId, ObjectId terminalId) {
		Assert.notNull(sonId, "Child id can not be null");
		Assert.notNull(terminalId, "Terminal Id can not be null");
		return appsInstalledRepository.countByIdAndSonId(terminalId, sonId);
	}

}
