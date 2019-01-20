package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.List;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeDaysEnum;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddKidRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddPhoneNumberBlockedDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppRulesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppStatsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveCallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveDayScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveFunTimeScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSmsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.TerminalHeartbeatDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledInTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppRuleDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppStatsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DayScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.FunTimeScheduledDTO;
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
public interface ITerminalService {
	
	/**
	 * 
	 * @param saveTerminalDTO
	 * @return
	 */
	TerminalDTO save(final SaveTerminalDTO saveTerminalDTO);
	
	/**
     * Get Terminals By Kid Id
     * @param id
     * @return
     */
    Iterable<TerminalDTO> getTerminalsByKidId(final String id);
    
    /**
     * Get Terminal Detail By Child Id And Terminal Id
     * @param id
     * @param terminalId
     * @return
     */
    TerminalDetailDTO getTerminalDetail(final ObjectId id, final String terminalId);
    
    /**
     * Delete By Id
     * @param id
     */
    void deleteById(final ObjectId id);
    
    /**
     * 
     * @param id
     * @param terminalId
     * @param text
     * @return
     */
    Iterable<AppInstalledDTO> getAllAppsInstalledInTheTerminal(final ObjectId id, 
    		final ObjectId terminalId, final String text);
    
    /**
     * 
     * @param id
     * @param text
     * @return
     */
    Iterable<AppInstalledInTerminalDTO> getAllAppsInstalledByKid(final ObjectId id, final String text);
    
    
    /**
     * Get Count Apps Installed In The terminal
     * @param kid
     * @param terminalId
     * @return
     */
    long getCountAppsInstalledInTheTerminal(final ObjectId kid, final ObjectId terminalId);
    
    /**
     * Get Count Contacts In The terminal
     * @param kid
     * @param terminalId
     * @return
     */
    long getCountContactsInTheTerminal(final ObjectId kid, final ObjectId terminalId);
    
    
    /**
     * Get Count Calls In The terminal
     * @param kid
     * @param terminalId
     * @return
     */
    long getCountCallsInTheTerminal(final ObjectId kid, final ObjectId terminalId);
    
    
    /**
     * Get Count SMS In The terminal
     * @param kid
     * @param terminalId
     * @return
     */
    long getCountSmsInTheTerminal(final ObjectId kid, final ObjectId terminalId);
    
    
    /**
     * Save
     * @param saveAppInstalledDTO
     * @return
     */
    AppInstalledDTO save(final SaveAppInstalledDTO saveAppInstalledDTO);
    
    /**
     * 
     * @param saveAppsInstalledDTO
     * @return
     */
    Iterable<AppInstalledDTO> save(final Iterable<SaveAppInstalledDTO> saveAppsInstalledDTO);
    
    /**
     * Delete Apps
     * @param kid
     * @param terminalId
     */
    void deleteApps(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Delete Apps
     * @param kid
     * @param terminalId
     * @param apps
     */
    void deleteApps(final ObjectId kid, final ObjectId terminal, final List<ObjectId> apps);
    
    /**
     * Delete App installed by child id
     * @param appId
     */
    void deleteAppInstalledById(final ObjectId appId) throws Throwable;
    
    
    
    /**
     * Get Terminal by id and kid id
     * @param terminalId
     * @param id
     * @return
     */
    TerminalDTO getTerminalByIdAndKidId(final ObjectId terminalId, final ObjectId id);
    
    /**
     * Save App Rules
     * @param appRulesList
     */
    void saveAppRules(final Iterable<SaveAppRulesDTO> appRulesList);
    
    /**
     * Save App Rules
     * @param appRules
     */
    void saveAppRules(final SaveAppRulesDTO appRules);
    
    /**
     * Get App Installed
     * @param app
     * @param terminal
     * @return
     */
    AppInstalledDTO getAppInstalled(final ObjectId app, final ObjectId terminal);
    
    /**
     * Get Detail Of Calls
     * @param kid
     * @param terminal
     * @return
     */
    Iterable<CallDetailDTO> getDetailOfCalls(final ObjectId kid, final ObjectId terminal);
    
    
    /**
     * Get Detail of the call
     * @param kid
     * @param terminal
     * @param call
     * @return
     */
    CallDetailDTO getDetailOfTheCall(final ObjectId kid, final ObjectId terminal, final ObjectId call);

    /**
     * Delete All Call Details
     * @param kid
     * @param terminal
     */
    void deleteAllCallDetails(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Delete Call Detail
     * @param kid
     * @param terminal
     * @param call
     */
    void deleteCallDetail(final ObjectId kid, final ObjectId terminal, final ObjectId call);
    
    
    /**
     * Delete Call Detail
     * @param kid
     * @param terminal
     * @param callList
     */
    void deleteCallDetail(final ObjectId kid, final ObjectId terminal, final List<ObjectId> callList);
    
    
    /**
     * Get Sms List
     * @param kid
     * @param terminal
     * @return
     */
    Iterable<SmsDTO> getSmsList(final ObjectId kid, final ObjectId terminal);
    
    
    /**
     * Get SMS Detail
     * @param kid
     * @param terminal
     * @param sms
     * @return
     */
    SmsDTO getSmsDetail(final ObjectId kid, final ObjectId terminal, final ObjectId sms);

    /**
     * Delete All SMS
     * @param kid
     * @param terminal
     */
    void deleteAllSms(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Delete single sms
     * @param kid
     * @param terminal
     * @param smsList
     */
    void deleteSms(final ObjectId kid, final ObjectId terminal, final List<ObjectId> smsList);
    
    /**
     * Delete single sms
     * @param kid
     * @param terminal
     * @param sms
     */
    void deleteSms(final ObjectId kid, final ObjectId terminal, final ObjectId sms);
    
    /**
     * Add Phone Number Blocked
     * @param addPhoneNumber
     * @return
     */
    PhoneNumberBlockedDTO addPhoneNumberBlocked(
    		final AddPhoneNumberBlockedDTO addPhoneNumber);
    
    
    /**
     * Remove Phone Number Blocked
     * @param kid
     * @param terminal
     * @param blockIdentity
     */
    void removePhoneNumberBlocked(final String kid, final String terminal, final String blockIdentity);
    
    /**
     * Get Phone Numbers Blocked
     * @param kid
     * @param terminal
     * @return
     */
    Iterable<PhoneNumberBlockedDTO> getPhoneNumbersBlocked(final String kid, final String terminal);
    
    
    /**
     * Save SMS
     * @param sms
     * @return
     */
    SmsDTO saveSms(final SaveSmsDTO sms);
    
    /**
     * Save SMS
     * @param smsList
     * @return
     */
    Iterable<SmsDTO> saveSms(final List<SaveSmsDTO> smsList);
    
    
    /**
     * Save Calls
     * @param smsList
     * @return
     */
    Iterable<CallDetailDTO> saveCalls(final List<SaveCallDetailDTO> calls);
    
    /**
     * Save Call
     * @param call
     * @return
     */
    CallDetailDTO saveCall(final SaveCallDetailDTO call);
    
    /**
     * UnBlock All Phone Numbers
     */
    void unBlockAllPhoneNumbers();
    
    /**
     * Unblock Phone Number
     * @param kid
     * @param terminal
     * @param phoneNumber
     */
    void unBlockPhoneNumber(final ObjectId kid, final ObjectId terminal, final String idOrPhonenumber);

    /**
     * Get Contact
     * @param id
     * @param terminal
     * @param kid
     * @return
     */
    ContactDTO getContactByIdAndTerminalIdAndKidId(final ObjectId id, final ObjectId terminal, final ObjectId kid);

    /**
     * Get Contacts
     * @param kid
     * @param terminal
     * @param text
     * @return
     */
    Iterable<ContactDTO> getContacts(final ObjectId kid, final ObjectId terminal, final String text);
    
    /**
     * Delete All Contacts
     * @param kid
     * @param terminal
     */
    void deleteAllContacts(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Delete Contact
     * @param kid
     * @param terminal
     * @param contact
     */
    void deleteContact(final ObjectId kid, final ObjectId terminal, final ObjectId contact);
    
    /**
     * Delete Contact
     * @param kid
     * @param terminal
     * @param contacts
     */
    void deleteContacts(final ObjectId kid, final ObjectId terminal, final List<ObjectId> contacts);
    
    /**
     * 
     * @param contacts
     * @return
     */
    Iterable<ContactDTO> saveContacts(final List<SaveContactDTO> contacts);
    
    /**
     * Save Contact
     * @param contact
     * @return
     */
    ContactDTO saveContact(final SaveContactDTO contact);
    
    /**
     * Save Heartbeat
     * @param terminalHeartbeat
     */
    void saveHeartbeat(final TerminalHeartbeatDTO terminalHeartbeat);
    
    /**
     * Get App Rules
     * @param kid
     * @param terminal
     * @return
     */
    Iterable<AppRuleDTO> getAppRules(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Get App Rules
     * @param kid
     * @param terminal
     * @param app
     * @return
     */
    AppRuleDTO getAppRules(final ObjectId kid, final ObjectId terminal, final ObjectId app);
    
    /**
     * Save App Stats
     * @param appStatsDTO
     * @return
     */
    AppStatsDTO saveAppStats(final SaveAppStatsDTO appStatsDTO);
    
    /**
     * Save App Stats
     * @param appStatsDTO
     * @return
     */
    Iterable<AppStatsDTO> saveAppStats(final Iterable<SaveAppStatsDTO> appStatsDTO);
    
    /**
     * Get Stats for app installed
     * @param kid
     * @param terminal
     * @param total
     * @return
     */
    Iterable<AppStatsDTO> getStatsForAppInstalled(final ObjectId kid, 
    		final ObjectId terminal, final Integer total);
    
    /**
     * Get Stats for app
     * @param kid
     * @param terminal
     * @param app
     * @return
     */
    AppStatsDTO getStatsForApp(final ObjectId kid, 
    		final ObjectId terminal, final ObjectId app);
    
    
    /**
     * Delete Stats For Apps Installed
     * @param kid
     * @param terminal
     * @param ids
     */
    void deleteStatsForAppsInstalled(final ObjectId kid, 
    		final ObjectId terminal, final List<ObjectId> ids);
    
    /**
     * Enable App In The Terminal
     * @param kid
     * @param terminal
     * @param app
     */
    void enableAppInTheTerminal(final ObjectId kid, final ObjectId terminal,
    		final ObjectId app);
    
    /**
     * Disable App In The Terminal
     * @param kid
     * @param terminal
     * @param app
     */
    void disableAppInTheTerminal(final ObjectId kid, final ObjectId terminal,
    		final ObjectId app);
    
    
    /**
     * Enable Bed Time In The Terminal
     * @param kid
     * @param terminal
     */
    void enableBedTimeInTheTerminal(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Disable Bed Time In The Terminal
     * @param kid
     * @param terminal
     */
    void disableBedTimeInTheTerminal(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Lock Screen In The Terminal
     * @param kid
     * @param terminal
     */
    void lockScreenInTheTerminal(final ObjectId kid, final ObjectId terminal);
    
    /**
     * UnLock Screen In The Terminal
     * @param kid
     * @param terminal
     */
    void unlockScreenInTheTerminal(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Lock Camera In The Terminal
     * @param kid
     * @param terminal
     */
    void lockCameraInTheTerminal(final ObjectId kid, final ObjectId terminal);
    
    /**
     * UnLock Camera In the Terminal
     * @param kid
     * @param terminal
     */
    void unlockCameraInTheTerminal(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Enable Settings In The Terminal
     * @param kid
     * @param terminal
     */
    void enableSettingsInTheTerminal(final ObjectId kid, final ObjectId terminal);
    
    /**
     * DIsable Settings In the Terminal
     * @param kid
     * @param terminal
     */
    void disableSettingsInTheTerminal(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Add Kid Request
     * @param kidRequest
     * @return
     */
    KidRequestDTO addKidRequest(final AddKidRequestDTO kidRequest);
    
    /**
     * Get All Kid Request For Kid
     * @param kid
     * @return
     */
    Iterable<KidRequestDTO> getAllKidRequestForKid(final ObjectId kid);
    
    /**
     * Get Kid Request Detail
     * @param kid
     * @param request
     * @return
     */
    KidRequestDTO getKidRequestDetail(final ObjectId kid, final ObjectId request);
    
    /**
     * Delete All Kid Request By Kid
     * @param kid
     */
    void deleteAllKidRequestByKid(final ObjectId kid);
    
    /**
     * Delete Kid Request
     * @param kid
     * @param request
     */
    void deleteKidRequest(final ObjectId kid, final List<ObjectId> request);
    
    /**
     * Delete Kid Request
     * @param kid
     * @param id
     */
    void deleteKidRequest(final ObjectId kid, final ObjectId id);
    
    /**
     * Get Fun Time Scheduled By Kid
     * @param kid
     * @param terminal
     * @return
     */
    FunTimeScheduledDTO getFunTimeScheduledByKid(final ObjectId kid, final ObjectId terminal);
    

    /**
     * Get Fun Time Scheduled By Kid
     * @param kid
     * @param terminal
     * @param day
     * @return
     */
    DayScheduledDTO getFunTimeDayScheduled(final ObjectId kid, final ObjectId terminal, final FunTimeDaysEnum day);
    
    /**
     * Save Fun Time Scheduled By Kid
     * @param kid
     * @param terminal
     * @param funTimeScheduled
     * @return
     */
    FunTimeScheduledDTO saveFunTimeScheduledByKid(final ObjectId kid, final ObjectId terminal, final SaveFunTimeScheduledDTO funTimeScheduled);
    
    /**
     * Save Fun Time Day Scheduled
     */
    DayScheduledDTO saveFunTimeDayScheduled(final ObjectId kid, final ObjectId terminal, final FunTimeDaysEnum day, final SaveDayScheduledDTO saveDayScheduled);
    
}
