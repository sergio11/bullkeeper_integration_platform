package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalHeartbeatEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveTerminalHeartBeatConfigurationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalHeartbeatDTO;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Mapper
public abstract class TerminalHeartbeatEntityDataMapper {
	
	/**
	 * Pretty Time
	 */
	@Autowired
	protected PrettyTime prettyTime;
	
	 /**
     * 
     * @param terminalEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "terminalHeartbeatEntityToTerminalHeartbeatDTO")
    public abstract Iterable<TerminalHeartbeatDTO> terminalHeartbeatEntityToTerminalHeartbeatDTO(Iterable<TerminalHeartbeatEntity> terminalHeartbeatEntities);
   
    /**
   	 * Terminal Entity To Terminal DTO
   	 * @param terminalEntity
   	 * @return
   	 */
     @Mappings({
         @Mapping(expression="java(prettyTime.format(terminalHeartbeatEntity.getLastTimeNotified()))", 
           		target="lastTimeNotifiedSince"),
         @Mapping(source = "terminalHeartbeatEntity.lastTimeNotified", target = "lastTimeNotified", dateFormat = "yyyy/MM/dd HH:mm:ss.SSS"),
       })
     @Named("terminalHeartbeatEntityToTerminalHeartbeatDTO")
     public abstract TerminalHeartbeatDTO terminalHeartbeatEntityToTerminalHeartbeatDTO(final TerminalHeartbeatEntity terminalHeartbeatEntity); 
  
     /**
      * Terminal Entity To Terminal DTO
    	 * @param terminalEntity
    	 * @return
    	 */
      @Mappings({})
      @Named("saveTerminalHeartBeatConfigurationDTOToTerminalHeartbeatEntity")
      public abstract TerminalHeartbeatEntity saveTerminalHeartBeatConfigurationDTOToTerminalHeartbeatEntity(final SaveTerminalHeartBeatConfigurationDTO saveTerminalHeartBeatConfigurationDTO); 
  
     
}
