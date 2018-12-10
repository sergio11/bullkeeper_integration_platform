package sanchez.sanchez.sergio.bullkeeper.domain.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppRulesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CallDetailDTO;
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
     * @return
     */
    Iterable<AppInstalledDTO> getAllAppsInstalledInTheTerminal(final ObjectId id, 
    		final ObjectId terminalId);
    
    
    /**
     * Get Count Apps Installed In The terminal
     * @param id
     * @param terminalId
     * @return
     */
    long getCountAppsInstalledInTheTerminal(final ObjectId id, final ObjectId terminalId);
    
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
     * Delete Apps Installed By Child Id And Terminal Id
     * @param id
     * @param terminalId
     */
    void deleteAppsInstalledByKidIdAndTerminalId(final ObjectId id, final ObjectId terminalId);
    
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
     * @param sms
     */
    void deleteSms(final ObjectId kid, final ObjectId terminal, final ObjectId sms);
    
}
