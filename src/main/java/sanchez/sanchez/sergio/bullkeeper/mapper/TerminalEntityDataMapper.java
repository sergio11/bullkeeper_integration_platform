package sanchez.sanchez.sergio.bullkeeper.mapper;

import java.util.List;

import org.bson.types.ObjectId;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.domain.inject.BeansManager;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDetailDTO;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Mapper
public abstract class TerminalEntityDataMapper {
	
	/**
	 * Son Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Bean Manager
	 */
	@Autowired
	protected BeansManager beanManager;
	
	/**
	 * Pretty Time
	 */
	@Autowired
	protected PrettyTime prettyTime;
	
	/**
	 * Get Count Apps Installed In The terminal
	 * @param kid
	 * @param terminal
	 * @return
	 */
	protected long getCountAppsInstalledInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		final ITerminalService terminalService = beanManager.getTerminalService();
		return terminalService.getCountAppsInstalledInTheTerminal(kid, terminal);
	}
	
	/**
	 * Get Count Sms In The terminal
	 * @param terminal
	 * @param terminal
	 * @return
	 */
	protected long getCountSmsInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		final ITerminalService terminalService = beanManager.getTerminalService();
		return terminalService.getCountSmsInTheTerminal(kid, terminal);
	}
	
	
	/**
	 * Get Count Contacts In The terminal
	 * @param kid
	 * @param terminal
	 * @return
	 */
	protected long getCountContactsInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		final ITerminalService terminalService = beanManager.getTerminalService();
		return terminalService.getCountContactsInTheTerminal(kid, terminal);
	}
	
	/**
	 * Get Count Calls In The terminal
	 * @param kid
	 * @param terminal
	 * @return
	 */
	protected long getCountCallsInTheTerminal(final ObjectId kid, final ObjectId terminal) {
		final ITerminalService terminalService = beanManager.getTerminalService();
		return terminalService.getCountCallsInTheTerminal(kid, terminal);
	}
	

	/**
	 * Terminal Entity To Terminal DTO
	 * @param terminalEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(terminalEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(terminalEntity.getKid().getId().toString())", target = "kid" ),
        @Mapping(expression="java(terminalEntity.getStatus().name())", target="status")
    })
    @Named("terminalEntityToTerminalDTO")
    public abstract TerminalDTO terminalEntityToTerminalDTO(final TerminalEntity terminalEntity); 
    
  
    /**
     * 
     * @param terminalEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "terminalEntityToTerminalDTO")
    public abstract Iterable<TerminalDTO> terminalEntityToTerminalDTO(Iterable<TerminalEntity> terminalEntities);
    
	
	/**
	 * 
	 * @param saveTerminalDTO
	 * @return
	 */
	@Mappings({
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveTerminalDTO.getKid())))", 
    			target = "kid" )
    })
    @Named("saveTerminalDtoToTerminalEntity")
    public abstract TerminalEntity saveTerminalDtoToTerminalEntity(SaveTerminalDTO saveTerminalDTO);

	/**
	 * 
	 * @param saveSocialMediaEntities
	 * @return
	 */
	@IterableMapping(qualifiedByName = "saveTerminalDtoToTerminalEntity")
    public abstract List<TerminalEntity> saveTerminalDtoToTerminalEntity(List<SaveTerminalDTO> saveTerminalDTOs);
    
	
	/**
	 * 
	 * @param saveSocialMediaEntities
	 * @return
	 */
    @IterableMapping(qualifiedByName = "saveTerminalDtoToTerminalEntity")
    public abstract List<TerminalEntity> saveTerminalDtoToTerminalEntity(Iterable<SaveTerminalDTO> saveTerminalDTOs);
	
    /**
   	 * Terminal Entity To Terminal DTO
   	 * @param terminalEntity
   	 * @return
   	 */
       @Mappings({
           @Mapping(expression="java(terminalEntity.getId().toString())", 
           		target = "identity" ),
           @Mapping(expression="java(terminalEntity.getKid().getId().toString())", 
           		target = "kid" ),
           @Mapping(expression="java(getCountAppsInstalledInTheTerminal(terminalEntity.getKid().getId(), terminalEntity.getId()))", 
           		target = "totalApps"),
           @Mapping(expression="java(getCountSmsInTheTerminal(terminalEntity.getKid().getId(), terminalEntity.getId()))", 
      			target = "totalSms"),
           @Mapping(expression="java(getCountCallsInTheTerminal(terminalEntity.getKid().getId(), terminalEntity.getId()))", 
 				target = "totalCalls"),
           @Mapping(expression="java(getCountContactsInTheTerminal(terminalEntity.getKid().getId(), terminalEntity.getId()))", 
 				target = "totalContacts"),
           @Mapping(expression="java(prettyTime.format(terminalEntity.getLastTimeUsed()))", 
           		target="lastTimeUsed"),
           @Mapping(expression="java(terminalEntity.getScreenStatus().name())", 
      			target="screenStatus")
       })
       @Named("terminalEntityToTerminalDetailDTO")
       public abstract TerminalDetailDTO terminalEntityToTerminalDetailDTO(final TerminalEntity terminalEntity); 
   	
    
    
}
