package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

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

import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.exception.AppInstalledNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.mapper.AppInstalledEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.CallDetailEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.ContactEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.PhoneNumberEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.SmsEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.TerminalEntityDataMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppInstalledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppRuleEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CallDetailEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PhoneNumberBlockedEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppInstalledRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CallDetailRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ContactEntityRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PhoneNumberBlockedRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SmsRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddPhoneNumberBlockedDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppRulesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveCallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSmsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.TerminalHeartbeatDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppRuleDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO;
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
			final ContactEntityMapper contactEntityMapper) {
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
	 * un block phone number
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
		Assert.notNull(text, "Text can not be null");
		
		// Find All Contacts
		final Iterable<ContactEntity> contacts = 
				text.isEmpty() ? 
						contactRepository.findAllByKidIdAndTerminalId(kid, terminal)
						: contactRepository.findAllByKidIdAndTerminalIdAndNameIgnoreCaseContaining(kid, terminal, text);
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


}
