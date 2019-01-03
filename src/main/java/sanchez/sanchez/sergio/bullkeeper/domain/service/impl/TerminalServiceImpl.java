package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.exception.AppInstalledNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.PhoneNumberAlreadyBlockedException;
import sanchez.sanchez.sergio.bullkeeper.exception.PreviousRequestHasNotExpiredYetException;
import sanchez.sanchez.sergio.bullkeeper.mapper.AppInstalledEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.AppStatsEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.CallDetailEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.ContactEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.KidRequestEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.PhoneNumberEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.SmsEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.TerminalEntityDataMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppInstalledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppRuleEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppStatsEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CallDetailEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidRequestEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PhoneNumberBlockedEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppInstalledRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppStatsRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CallDetailRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ContactEntityRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRequestRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PhoneNumberBlockedRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SmsRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddKidRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddPhoneNumberBlockedDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppRulesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppStatsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveCallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSmsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.TerminalHeartbeatDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppRuleDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppStatsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.PhoneNumberBlockedDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SmsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDetailDTO;

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
	private final TerminalRepository terminalRepository;
	
	/**
	 * App Installed Repository
	 */
	private final AppInstalledRepository appsInstalledRepository;
	
	/**
	 * App Installed Entity data mapper
	 */
	private final AppInstalledEntityMapper appInstalledEntityDataMapper;
	
	/**
	 * Call Detail Repository
	 */
	private final CallDetailRepository callDetailRepository;
	
	/**
	 * Call Detail Entity Mapper
	 */
	private final CallDetailEntityMapper callDetailEntityMapper;
	
	
	/**
	 * SMS Repository
	 */
	private final SmsRepository smsRepository;
	
	/**
	 * Sms Entity Mapper
	 */
	private final SmsEntityMapper smsEntityMapper;
	
	/**
	 * Phone Number Blocked Repository
	 */
	private final PhoneNumberBlockedRepository phoneNumberBlockedRepository;
	
	/**
	 * Phone Number Mapper
	 */
	private final PhoneNumberEntityMapper phoneNumberEntityMapper;
	
	/**
	 * Contact Entity Repoitory
	 */
	private final ContactEntityRepository contactRepository;
	
	/**
	 * Contact Entity Mapper
	 */
	private final ContactEntityMapper contactEntityMapper;
	
	/**
	 * App Stats Repository
	 */
	private final AppStatsRepository appStatsRepository;
	
	/**
	 * App Stats Entity Mapper
	 */
	private final AppStatsEntityMapper appStatsEntityMapper;
	
	/**
	 * Kid Request Repository
	 */
	private final KidRequestRepository kidRequestRepository;
	
	/**
	 * Kid Request Mapper
	 */
	private final KidRequestEntityMapper kidRequestMapper;
	

	/**
	 * 
	 * @param terminalEntityDataMapper
	 * @param terminalRepository
	 * @param appsInstalledRepository
	 * @param appInstalledEntityDataMapper
	 * @param callDetailRepository
	 * @param callDetailEntityMapper
	 * @param smsRepository
	 * @param smsEntityMapper
	 * @param phoneNumberBlockedRepository
	 * @param phoneNumberEntityMapper
	 * @param appStatsRepository
	 * @param appStatsEntityMapper
	 * @param kidRequestRepository
	 * @param kidRequestMapper
	 */
	@Autowired
	public TerminalServiceImpl(final TerminalEntityDataMapper terminalEntityDataMapper, 
			final TerminalRepository terminalRepository, final AppInstalledRepository appsInstalledRepository,
			final AppInstalledEntityMapper appInstalledEntityDataMapper,
			final CallDetailRepository callDetailRepository,
			final CallDetailEntityMapper callDetailEntityMapper, 
			final SmsRepository smsRepository, 
			final SmsEntityMapper smsEntityMapper,
			final PhoneNumberBlockedRepository phoneNumberBlockedRepository,
			final PhoneNumberEntityMapper phoneNumberEntityMapper,
			final ContactEntityRepository contactRepository,
			final ContactEntityMapper contactEntityMapper,
			final AppStatsRepository appStatsRepository,
			final AppStatsEntityMapper appStatsEntityMapper,
			final KidRequestRepository kidRequestRepository,
			final KidRequestEntityMapper kidRequestMapper) {
		super();
		this.terminalEntityDataMapper = terminalEntityDataMapper;
		this.terminalRepository = terminalRepository;
		this.appsInstalledRepository = appsInstalledRepository;
		this.appInstalledEntityDataMapper = appInstalledEntityDataMapper;
		this.callDetailRepository = callDetailRepository;
		this.callDetailEntityMapper = callDetailEntityMapper;
		this.smsRepository = smsRepository;
		this.smsEntityMapper = smsEntityMapper;
		this.phoneNumberBlockedRepository = phoneNumberBlockedRepository;
		this.phoneNumberEntityMapper = phoneNumberEntityMapper;
		this.contactRepository = contactRepository;
		this.contactEntityMapper = contactEntityMapper;
		this.appStatsRepository = appStatsRepository;
		this.appStatsEntityMapper = appStatsEntityMapper;
		this.kidRequestRepository = kidRequestRepository;
		this.kidRequestMapper = kidRequestMapper;
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
	public Iterable<TerminalDTO> getTerminalsByKidId(final String kid) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.hasLength(kid, "Kid id can not be empty");
		
		final List<TerminalEntity> terminalEntities = 
				terminalRepository.findByKidId(new ObjectId(kid));
		
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
	public Iterable<AppInstalledDTO> getAllAppsInstalledInTheTerminal(final ObjectId kid, final ObjectId terminalId, final String text) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminalId, "Terminal Id can not be null");
		Assert.notNull(text, "Text can not be null");
		
		// Find apps
		final Iterable<AppInstalledEntity> appInstalled = 
				!text.isEmpty() ? 
						appsInstalledRepository.findAllByTerminalIdAndKidIdAndAppNameIgnoreCaseContaining(
								terminalId, kid, text)
						: appsInstalledRepository.findAllByTerminalIdAndKidId(terminalId, kid);
				
				
		return appInstalledEntityDataMapper.appInstalledEntityToAppInstalledDTO(appInstalled);
	}

	/**
	 * Save App Installed DTO
	 */
	@Override
	public AppInstalledDTO save(SaveAppInstalledDTO saveAppInstalledDTO) {
		Assert.notNull(saveAppInstalledDTO, "Save App Installed can not be null");
		
		// Save App Installed DTO
		final AppInstalledEntity appIntalledToSave = 
				appInstalledEntityDataMapper
				.saveAppInstalledDtoToAppInstalledEntity(saveAppInstalledDTO);
				
		// Find By Package Name
		AppInstalledEntity appInstalledSaved = appsInstalledRepository
			.findOneByPackageName(appIntalledToSave.getPackageName());
		
		if(appInstalledSaved != null) 
			appIntalledToSave.setId(appInstalledSaved.getId());
		
		// Save App Installed
		appInstalledSaved = 
				appsInstalledRepository.save(appInstalledSaved);
		
		return appInstalledEntityDataMapper
				.appInstalledEntityToAppInstalledDTO(appInstalledSaved);
	}

	/**
	 * Save
	 */
	@Override
	public Iterable<AppInstalledDTO> save(Iterable<SaveAppInstalledDTO> saveAppsInstalledDTO) {
		Assert.notNull(saveAppsInstalledDTO, "Apps installed can not be null");
		
		final Iterable<AppInstalledEntity> appsInstalledToSave = 
				appInstalledEntityDataMapper.saveAppInstalledDtoToAppInstalledEntity(saveAppsInstalledDTO);
		
		for(final AppInstalledEntity appInstalledToSave: appsInstalledToSave) {
			
			final AppInstalledEntity appInstalledSaved = appsInstalledRepository
					.findOneByPackageName(appInstalledToSave.getPackageName());
			
			if(appInstalledSaved != null)
				appInstalledToSave.setId(appInstalledSaved.getId());
			
		}
		
		// Save apps installed list
		final Iterable<AppInstalledEntity> appsInstalledSaved = 
				appsInstalledRepository.save(appsInstalledToSave);
		
		return appInstalledEntityDataMapper.appInstalledEntityToAppInstalledDTO(appsInstalledSaved);
	
	}

	/**
	 * Delete Apps installed by child id and terminal id
	 */
	@Override
	public void deleteApps(final ObjectId kid, final ObjectId terminalId) {
		Assert.notNull(kid, "Kid id can not be null");
		Assert.notNull(terminalId, "Terminal id can not be null");
		
		appsInstalledRepository.deleteByKidIdAndTerminalId(kid, terminalId);
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
	 * Get Terminal By Id And KId Id
	 */
	@Override
	public TerminalDTO getTerminalByIdAndKidId(final ObjectId terminalId, final ObjectId kid) {
		Assert.notNull(terminalId, "Terminal id can not be null");
		Assert.notNull(kid, "Kid id can not be null");
		
		// Get terminal
		final TerminalEntity terminalEntity = terminalRepository
				.findByIdAndKidId(terminalId, kid);
	
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
	 * Save App Rules
	 */
	@Override
	public void saveAppRules(final SaveAppRulesDTO appRules) {
		Assert.notNull(appRules, "App Rules can not be null");
		
		appsInstalledRepository.updateAppRules(
				new ObjectId(appRules.getIdentity()), 
				AppRuleEnum.valueOf(appRules.getType()));
	
		
	}

	
	/**
	 * Get Terminal Detail BY kid Id And Terminal Id
	 */
	@Override
	public TerminalDetailDTO getTerminalDetail(final ObjectId kid, 
			final String terminalId) {
		Assert.notNull(kid, "Child id can not be null");
		Assert.notNull(terminalId, "Terminal Id can not be null");
		
		
		// Find By Id And Son Entity
		final TerminalEntity terminalEntity = ObjectId.isValid(terminalId) ?
				terminalRepository.findByIdAndKidId(new ObjectId(terminalId), kid):
					terminalRepository.findByDeviceIdAndKidId(terminalId, kid);
					
		
		// Transform to Terminal Detail
		return terminalEntityDataMapper.terminalEntityToTerminalDetailDTO(terminalEntity);
		
	}

	
	/**
	 * Get Count Apps Installed In The Terminal
	 */
	@Override
	public long getCountAppsInstalledInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "kid id can not be null");
		Assert.notNull(terminal, "Terminal Id can not be null");
		return appsInstalledRepository.countByKidIdAndTerminalId(kid, terminal);
	}

	/**
	 * Get App installed
	 */
	@Override
	public AppInstalledDTO getAppInstalled(final ObjectId app, final ObjectId terminal) {
		Assert.notNull(app, "App can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		return appInstalledEntityDataMapper.appInstalledEntityToAppInstalledDTO(
				appsInstalledRepository.findByIdAndTerminalId(app, terminal));
	}

	/**
	 * Get Detail of calls
	 */
	@Override
	public Iterable<CallDetailDTO> getDetailOfCalls(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Terminal can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		// Get Call Details from terminal
		final Iterable<CallDetailEntity> callsEntities = callDetailRepository
				.findByKidIdAndTerminalId(kid, terminal);
		// Map Results
		return callDetailEntityMapper.callDetailEntityToCallDetailDTOs(callsEntities);
	}

	/**
	 * Get Detail Of The call
	 */
	@Override
	public CallDetailDTO getDetailOfTheCall(final ObjectId kid, final ObjectId terminal, final ObjectId call) {
		Assert.notNull(kid, "Terminal can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(call, "Call can not be null");
		
		final CallDetailEntity callDetailEntity = 
				callDetailRepository.findOneByIdAndKidIdAndTerminalId(call, kid, terminal);
		// Map Result
		return callDetailEntityMapper.callDetailEntityToCallDetailDTO(callDetailEntity);
	}

	/**
	 * Delete All Call Details
	 */
	@Override
	public void deleteAllCallDetails(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Terminal can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
	
		callDetailRepository.deleteByKidIdAndTerminalId(kid, terminal);
	}

	/**
	 * Delete Call Detail
	 */
	@Override
	public void deleteCallDetail(final ObjectId kid, final ObjectId terminal, final ObjectId call) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		callDetailRepository.deleteByIdAndKidIdAndTerminalId(kid, terminal, call);
		
	}

	/**
	 * Get SMS List
	 */
	@Override
	public Iterable<SmsDTO> getSmsList(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		final Iterable<SmsEntity> smsEntityList = smsRepository
				.findByKidIdAndTerminalId(kid, terminal);
	
		return smsEntityMapper.smsEntityToSmsDTOs(smsEntityList);
	}

	/**
	 * Get Sms Detail
	 */
	@Override
	public SmsDTO getSmsDetail(final ObjectId kid, final ObjectId terminal, final ObjectId sms) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(sms, "sms can not be null");
		
		final SmsEntity smsEntity = smsRepository
				.findByIdAndKidIdAndTerminalId(sms, kid, terminal);
		
		return smsEntityMapper.smsEntityToSmsDTO(smsEntity);
	}

	/**
	 * Delete All SMS
	 */
	@Override
	public void deleteAllSms(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		smsRepository.deleteByKidIdAndTerminalId(kid, terminal);
	}

	/**
	 * Delete SMS
	 */
	@Override
	public void deleteSms(final ObjectId kid, final ObjectId terminal, final ObjectId sms) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(sms, "sms can not be null");
		
		smsRepository.deleteByIdAndKidIdAndTerminalId(sms, kid, terminal);
		
	}

	/**
	 * Add Phone Number Blocked
	 * @param kid
	 * @param addPhoneNumber
	 */
	@Override
	public PhoneNumberBlockedDTO addPhoneNumberBlocked(final AddPhoneNumberBlockedDTO addPhoneNumber) {
		Assert.notNull(addPhoneNumber, "Add Phone Number can not be null");
		
		// Map
		final PhoneNumberBlockedEntity phoneNumberBlocked = 
				phoneNumberEntityMapper.addPhoneNumberBlockedEntity(addPhoneNumber);
		
		
		if(phoneNumberBlockedRepository
			.countByPhoneNumberAndKidIdAndTerminalId(phoneNumberBlocked.getPhoneNumber(), 
					phoneNumberBlocked.getKid().getId(), phoneNumberBlocked.getTerminal().getId()) > 0)
			throw new PhoneNumberAlreadyBlockedException();
	
		// Save
		final PhoneNumberBlockedEntity phoneNumberBlockedSaved = 
				phoneNumberBlockedRepository.save(phoneNumberBlocked);
		// Map result
		return phoneNumberEntityMapper
				.phoneNumberBlockedEntityToPhoneNumberBlockedDTO(phoneNumberBlockedSaved);
		
	}

	/**
	 * Remove Phone Number Blocked
	 * @param kid
	 * @param terminal
	 * @param blockIdentity
	 */
	@Override
	public void removePhoneNumberBlocked(final String kid, final String terminal, final String blockIdentity) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(blockIdentity, "Block Identity can not be null");
		
		phoneNumberBlockedRepository
			.deleteByTerminalIdAndKidIdAndId(new ObjectId(terminal), new ObjectId(kid),
					new ObjectId(blockIdentity));
		
	}

	/**
	 * @param kid
	 * @param terminal
	 */
	@Override
	public Iterable<PhoneNumberBlockedDTO> getPhoneNumbersBlocked(
			final String kid, final String terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		return phoneNumberEntityMapper.phoneNumberBlockedEntityToPhoneNumberBlockedDTOs(
				phoneNumberBlockedRepository.findAll());
	}

	/**
	 * Save SMS
	 */
	@Override
	public Iterable<SmsDTO> saveSms(final List<SaveSmsDTO> smsList) {
		Assert.notNull(smsList, "Sms list can not be null");
		
		// Save Sms DTO To Sms Entity
		final Iterable<SmsEntity> smsEntityListToSave = 
						smsEntityMapper.saveSmsDtoToSmsEntities(smsList);
		
		for(final SmsEntity smsEntityToSave: smsEntityListToSave) {
			
			SmsEntity smsEntitySaved = smsRepository.findByLocalId(smsEntityToSave.getLocalId());
			
			if(smsEntitySaved != null) {
				smsEntityToSave.setId(smsEntitySaved.getId());
			}
		}
		
		final Iterable<SmsEntity> smsEntitiesSaved = 
				smsRepository.save(smsEntityListToSave);
		
		return smsEntityMapper.smsEntityToSmsDTOs(smsEntitiesSaved);
	}

	/**
	 * Save Calls
	 */
	@Override
	public Iterable<CallDetailDTO> saveCalls(final List<SaveCallDetailDTO> calls) {
		Assert.notNull(calls, "Sms list can not be null");
		
		final Iterable<CallDetailEntity> callDetailListToSave = 
				callDetailEntityMapper.saveCallDetailDtoToCallDetailEntities(calls);
				
		for(final CallDetailEntity callDetailEntityToSave: callDetailListToSave) {
			
			final CallDetailEntity callDetailEntitySaved = callDetailRepository
						.findOneByLocalId(callDetailEntityToSave.getLocalId());
					
			if(callDetailEntitySaved != null) {
				callDetailEntityToSave.setId(callDetailEntitySaved.getId());
			}
		}
				
		final Iterable<CallDetailEntity> callEntitiesSaved = 
				callDetailRepository.save(callDetailListToSave);
				
		return callDetailEntityMapper.callDetailEntityToCallDetailDTOs(callEntitiesSaved);
	}

	/**
	 * Save Sms
	 */
	@Override
	public SmsDTO saveSms(final SaveSmsDTO sms) {
		Assert.notNull(sms, "Sms can not be null");
		
		// Save Sms DTO To Sms Entity
		final SmsEntity smsEntityToSave = 
				smsEntityMapper.saveSmsDtoToSmsEntity(sms);
		
		// Find By Local Id
		SmsEntity smsEntity = smsRepository.findByLocalId(smsEntityToSave.getLocalId());
		
		if(smsEntity != null) 
			smsEntityToSave.setId(smsEntity.getId());
		
		final SmsEntity smsEntitySaved = smsRepository.save(smsEntityToSave);
		
		return smsEntityMapper.smsEntityToSmsDTO(smsEntitySaved);
	}

	/**
	 * Save Call
	 */
	@Override
	public CallDetailDTO saveCall(final SaveCallDetailDTO call) {
		Assert.notNull(call, "Call can not be null");
		
		// Map To Call Detail Entity
		final CallDetailEntity callDetailEntityToSave = callDetailEntityMapper
					.saveCallDetailDtoToCallDetailEntity(call);		
		
	
		final CallDetailEntity callDetailEntitySaved = 
				callDetailRepository.findOneByLocalId(callDetailEntityToSave.getLocalId());
		
		if(callDetailEntitySaved != null)
			callDetailEntityToSave.setId(callDetailEntitySaved.getId());
		
		// Save Call Detail
		final CallDetailEntity callDetailSaved = callDetailRepository.save(callDetailEntityToSave);
		
		return this.callDetailEntityMapper.callDetailEntityToCallDetailDTO(callDetailSaved);
	}

	/**
	 * un block all phone numbers
	 */
	@Override
	public void unBlockAllPhoneNumbers() {
		phoneNumberBlockedRepository.deleteAll();
	}

	/**
	 * unblock phone number
	 */
	@Override
	public void unBlockPhoneNumber(final ObjectId kid, final ObjectId terminal, 
			final String idOrPhonenumber) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(idOrPhonenumber, "Phone Number can not be null");
		
		
		if(ObjectId.isValid(idOrPhonenumber))
			phoneNumberBlockedRepository
				.deleteByIdAndKidIdAndTerminalId(new ObjectId(idOrPhonenumber), kid, terminal);
		else
			phoneNumberBlockedRepository
				.deleteByPhoneNumberAndKidIdAndTerminalId(idOrPhonenumber, kid, terminal);
		
	}

	/**
	 * Get Contact By Id And Terminal Id And Kid Id
	 */
	@Override
	public ContactDTO getContactByIdAndTerminalIdAndKidId(final ObjectId id, 
			final ObjectId terminal, final ObjectId kid) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		// Find One
		final ContactEntity contactEntity = contactRepository.findOneByIdAndKidIdAndTerminalId(id, kid, terminal);
		// Map Result
		return contactEntityMapper.contactEntityToContactDTO(contactEntity);
	}
	
	
	/**
	 * Get Contacts
	 */
	@Override
	public Iterable<ContactDTO> getContacts(final ObjectId kid, final ObjectId terminal, final String text) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		// Find All Contacts
		final Iterable<ContactEntity> contacts = 
				text != null && !text.isEmpty() ? 
						contactRepository.findAllByKidIdAndTerminalIdAndNameIgnoreCaseContaining(kid, terminal, text) :
						contactRepository.findAllByKidIdAndTerminalId(kid, terminal);
		// Map Results
		return contactEntityMapper.contactEntityToContactDTOs(contacts);
	}

	/**
	 * Delete All Contacts
	 */
	@Override
	public void deleteAllContacts(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		contactRepository.deleteAllByKidIdAndTerminalId(kid, terminal);
		
	}

	/**
	 * Delete Contact
	 */
	@Override
	public void deleteContact(final ObjectId kid, final ObjectId terminal, final ObjectId contact) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(contact, "Contact can not be null");
		
		contactRepository.deleteByIdAndKidIdAndTerminalId(contact, kid, terminal);
		
	}

	/**
	 * Save Contacts
	 */
	@Override
	public Iterable<ContactDTO> saveContacts(List<SaveContactDTO> contacts) {
		Assert.notNull(contacts, "Contacts can not be null");
		
		final Iterable<ContactEntity> contactsToSave = 
				this.contactEntityMapper.saveContactDTOToContactEntities(contacts);
		
		for(ContactEntity contactEntity: contactsToSave) {
			
			final ContactEntity currentContactSaved = 
					contactRepository.findOneByLocalId(contactEntity.getLocalId());
			
			if(currentContactSaved != null)
				contactEntity.setId(currentContactSaved.getId());
		}
		
		// Save Contacts
		final Iterable<ContactEntity> contactsSaved = 
				contactRepository.save(contactsToSave);
		
		// Map Results
		return contactEntityMapper.contactEntityToContactDTOs(contactsSaved);
		
	}

	/**
	 * Save Contact
	 */
	@Override
	public ContactDTO saveContact(SaveContactDTO contact) {
		Assert.notNull(contact, "Contact can not be null");
		
		final ContactEntity contactToSave = 
				contactEntityMapper.saveContactDTOToContactEntity(contact);
		
		final ContactEntity currentContactSaved = 
				contactRepository.findOneByLocalId(contactToSave.getLocalId());
		
		if(currentContactSaved != null)
			contactToSave.setId(currentContactSaved.getId());
		
		final ContactEntity contactSaved = contactRepository.save(contactToSave);
		
		return contactEntityMapper.contactEntityToContactDTO(contactSaved);
		
	}

	/**
	 * Save Heartbeat
	 */
	@Override
	public void saveHeartbeat(final TerminalHeartbeatDTO terminalHeartbeat) {
		Assert.notNull(terminalHeartbeat, "Terminal Heart Beat can not be null");
		
		terminalRepository.updateScreenStatus(new ObjectId(terminalHeartbeat.getTerminal()), 
				new ObjectId(terminalHeartbeat.getKid()), ScreenStatusEnum.valueOf(terminalHeartbeat.getScreenStatus()));
		
	}

	/**
	 * Get App Rules
	 * @param kid
	 * @param terminal
	 */
	@Override
	public Iterable<AppRuleDTO> getAppRules(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		// Get App Rules
		final Iterable<AppInstalledEntity> appsInstalled = 
				appsInstalledRepository.getAppRules(kid, terminal);
		// Map Result
		return appInstalledEntityDataMapper
				.appInstalledEntityToAppRuleDTOs(appsInstalled);
	}

	/**
	 * Get App Rules
	 * @param kid
	 * @param terminal
	 * @param app
	 */
	@Override
	public AppRuleDTO getAppRules(final ObjectId kid, final ObjectId terminal, final ObjectId app) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(app, "App can not be null");
		
		// Get App Rules
		final AppInstalledEntity appsInstalled = 
			appsInstalledRepository.getAppRules(kid, terminal, app);
		// Map Result
		return appInstalledEntityDataMapper
						.appInstalledEntityToAppRuleDTO(appsInstalled);
	}

	/**
	 * Delete Apps
	 */
	@Override
	public void deleteApps(final ObjectId kid, final ObjectId terminal, final List<ObjectId> apps) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(apps, "Apps can not be null");
		
		appsInstalledRepository.deleteByKidIdAndTerminalIdAndIdIn(kid, terminal, apps);
		
		
	}

	/**
	 * Delete Call Detail
	 */
	@Override
	public void deleteCallDetail(final ObjectId kid, final ObjectId terminal, final List<ObjectId> callList) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(callList, "Apps can not be null");
		
	
		callDetailRepository.deleteByKidIdAndTerminalIdAndIdIn(kid, terminal, callList);
		
	}

	/**
	 * Delete SMS
	 */
	@Override
	public void deleteSms(final ObjectId kid, final ObjectId terminal, final List<ObjectId> smsList) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(smsList, "Sms can not be null");
		
		
		smsRepository.deleteByKidIdAndTerminalIdAndIdIn(kid, terminal, smsList);
		
	}

	/**
	 * Delete Contacts
	 */
	@Override
	public void deleteContacts(final ObjectId kid, final ObjectId terminal, final List<ObjectId> contacts) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(contacts, "Contacts can not be null");
		
		contactRepository.deleteByKidIdAndTerminalIdAndIdIn(kid, terminal, contacts);
		
	}

	/**
	 * Get Count Contacts in the terminal
	 */
	@Override
	public long getCountContactsInTheTerminal(final ObjectId kid, final ObjectId terminalId) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminalId, "Terminal can not be null");
		
		return contactRepository.countByKidIdAndTerminalId(kid, terminalId);
	}

	/**
	 * Get Count Calls In the terminal
	 */
	@Override
	public long getCountCallsInTheTerminal(final ObjectId kid, final ObjectId terminalId) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminalId, "Terminal can not be null");
		
		return callDetailRepository.countByKidIdAndTerminalId(kid, terminalId);
	}

	/**
	 * Get Count SMS in the terminal
	 */
	@Override
	public long getCountSmsInTheTerminal(final ObjectId kid, final ObjectId terminalId) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminalId, "Terminal can not be null");
		
		return smsRepository.countByKidIdAndTerminalId(kid, terminalId);
	}

	/**
	 * Save App Stats
	 */
	@Override
	public AppStatsDTO saveAppStats(final SaveAppStatsDTO appStatsDTO) {
		Assert.notNull(appStatsDTO, "App Stats can not be null");
		
		// App Stats To Save
		final AppStatsEntity appStatsToSave = this.appStatsEntityMapper
				.saveAppStatsDtoToAppStatsEntity(appStatsDTO);
		
		// Save App Stats
		final AppStatsEntity appStatsSaved = 
				appStatsRepository.save(appStatsToSave);
		// Map Result
		return appStatsEntityMapper.appStatsEntityToAppStatsDTO(appStatsSaved);
	}

	/**
	 * Get Stats For App Installed
	 */
	@Override
	public Iterable<AppStatsDTO> getStatsForAppInstalled(final ObjectId kid, 
			final ObjectId terminal, final Integer total) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(total, "Total can not be null");
		
		// Page Request
		final PageRequest pageRequest = new PageRequest(0, total);
		
		// Find By Terminal Id And Kid 
		final Page<AppStatsEntity> appStatsPage = appStatsRepository.findByTerminalIdAndKidId(
				terminal, kid, pageRequest);
		
		// Map to App Stats
		return appStatsPage.map(new Converter<AppStatsEntity, AppStatsDTO>() {
            @Override
            public AppStatsDTO convert(AppStatsEntity appStatsEntity) {
                return appStatsEntityMapper.appStatsEntityToAppStatsDTO(appStatsEntity);
            }
        });
	}

	/**
	 * Get Stats For App
	 */
	@Override
	public AppStatsDTO getStatsForApp(final ObjectId kid, final ObjectId terminal, 
			final ObjectId app) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(app, "Kid can not be null");
		// Find App Stats
		final AppStatsEntity appsStatsEntity = appStatsRepository
				.findOneByAppIdAndTerminalIdAndKidId(app, terminal, kid);
		// Map Results
		return appStatsEntityMapper.appStatsEntityToAppStatsDTO(appsStatsEntity);
	}

	/**
	 * Save App Stats
	 */
	@Override
	public Iterable<AppStatsDTO> saveAppStats(Iterable<SaveAppStatsDTO> appStatsDTO) {
		Assert.notNull(appStatsDTO, "App Stats DTO can not be null");
		final List<AppStatsDTO> appsSavedList = new ArrayList<>();
		for(final SaveAppStatsDTO saveAppStats: appStatsDTO)
			appsSavedList.add(saveAppStats(saveAppStats));
		return appsSavedList;
	}

	/**
	 * Enable App In the terminal
	 */
	@Override
	public void enableAppInTheTerminal(final ObjectId kid, 
			final ObjectId terminal, final ObjectId app) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(app, "App can not be null");
		
		this.appsInstalledRepository.enableAppInTheTerminal(
				kid, terminal, app);
		
	}

	/**
	 * Disable App In The Terminal
	 */
	@Override
	public void disableAppInTheTerminal(final ObjectId kid, 
			final ObjectId terminal, final ObjectId app) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(app, "App can not be null");
		
		this.appsInstalledRepository.disableAppInTheTerminal(
				kid, terminal, app);
		
	}

	/**
	 * Enable Bed Time In The Terminal
	 */
	@Override
	public void enableBedTimeInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		this.terminalRepository.enableBedTime(kid, terminal);
		
	}

	/**
	 * Disable Bed Time In The Terminal
	 */
	@Override
	public void disableBedTimeInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		this.terminalRepository.disableBedTime(kid, terminal);
	}

	/**
	 * Lock Screen In The Terminal
	 */
	@Override
	public void lockScreenInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		terminalRepository.lockScreen(kid, terminal);
	}

	/**
	 * Unlock Screen In The Terminal
	 */
	@Override
	public void unlockScreenInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		terminalRepository.unlockScreen(kid, terminal);
		
	}

	/**
	 * Lock Camera In The Terminal
	 */
	@Override
	public void lockCameraInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		terminalRepository.lockCamera(kid, terminal);
	}

	/**
	 * Unlock Camera In The Terminal
	 */
	@Override
	public void unlockCameraInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		terminalRepository.unlockCamera(kid, terminal);
	}

	/**
	 * Delete Stats For Apps Installed
	 */
	@Override
	public void deleteStatsForAppsInstalled(final ObjectId kid, 
			final ObjectId terminal, final List<ObjectId> ids) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(ids, "Ids can not be null");
		
		appStatsRepository.deleteByKidIdAndTerminalIdAndIdIn(kid, 
				terminal, ids);
	}
	
	/**
	 * Add Kid Rquest
	 */
	@Override
	public KidRequestDTO addKidRequest(final AddKidRequestDTO kidRequest) {
		Assert.notNull(kidRequest, "Kid request can not be null");
		
		// Map to Kid Request Entity
		final KidRequestEntity kidRequestEntityToSave = 
				kidRequestMapper.addKidRequestDTOToKidRequestEntity(kidRequest);
		
		if(kidRequestEntityToSave.getExpiredAt().after(new Date())) 
			throw new PreviousRequestHasNotExpiredYetException();
		
		// Save request
		final KidRequestEntity kidRequestSaved = 
				kidRequestRepository.save(kidRequestEntityToSave);
		
		// Map Result
		return kidRequestMapper.kidRequestEntityToKidRequestDTO(kidRequestSaved);
	}

	/**
	 * Get All Kid Request for kid
	 */
	@Override
	public Iterable<KidRequestDTO> getAllKidRequestForKid(final ObjectId kid) {
		Assert.notNull(kid, "Kid can not be null");
		
		// Find All By Kid
		final Iterable<KidRequestEntity> kidRequestEntities = 
				kidRequestRepository.findAllByKid(kid);
		// Map Result
		return kidRequestMapper.kidRequestEntityToKidRequestDTOs(kidRequestEntities);
	}

	/**
	 * Delete All Kid Request By Kid
	 */
	@Override
	public void deleteAllKidRequestByKid(final ObjectId kid) {
		Assert.notNull(kid, "Kid can not be null");
		
		// Delete All By Kid
		kidRequestRepository.deleteAllByKid(kid);
		
	}

	/**
	 * Delete Kid Request
	 */
	@Override
	public void deleteKidRequest(final ObjectId kid, final List<ObjectId> ids) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(ids, "ids can not be null");
		
		// Delete All By Kid And Id In
		kidRequestRepository.deleteAllByKidAndIdIn(kid, ids);
	}
}
