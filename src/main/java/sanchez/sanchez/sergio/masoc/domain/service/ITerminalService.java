package sanchez.sanchez.sergio.masoc.domain.service;

import org.bson.types.ObjectId;
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
public interface ITerminalService {
	
	/**
	 * 
	 * @param saveTerminalDTO
	 * @return
	 */
	TerminalDTO save(final SaveTerminalDTO saveTerminalDTO);
	
	/**
     * Get Terminals By Child Id
     * @param userId
     * @return
     */
    Iterable<TerminalDTO> getTerminalsByChildId(final String childId);
    
    /**
     * Get Terminal Detail By Child Id And Terminal Id
     * @param childId
     * @param terminalId
     * @return
     */
    TerminalDetailDTO getTerminalDetail(final ObjectId childId, final String terminalId);
    
    /**
     * Delete By Id
     * @param id
     */
    void deleteById(final ObjectId id);
    
    /**
     * 
     * @param sonId
     * @param terminalId
     * @return
     */
    Iterable<AppInstalledDTO> getAllAppsInstalledInTheTerminal(final ObjectId sonId, final ObjectId terminalId);
    
    
    /**
     * Get Count Apps Installed In The terminal
     * @param sonId
     * @param terminalId
     * @return
     */
    long getCountAppsInstalledInTheTerminal(final ObjectId sonId, final ObjectId terminalId);
    
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
     * @param childId
     * @param terminalId
     */
    void deleteAppsInstalledByChildIdAndTerminalId(final ObjectId childId, final ObjectId terminalId);
    
    /**
     * Delete App installed by child id
     * @param appId
     */
    void deleteAppInstalledById(final ObjectId appId) throws Throwable;
    
    /**
     * Get Terminal by id and child id
     * @param terminalId
     * @param childId
     * @return
     */
    TerminalDTO getTerminalByIdAndChildId(final ObjectId terminalId, final ObjectId childId);
    
    /**
     * Save App Rules
     * @param appRulesList
     */
    void saveAppRules(final Iterable<SaveAppRulesDTO> appRulesList);
    
}
