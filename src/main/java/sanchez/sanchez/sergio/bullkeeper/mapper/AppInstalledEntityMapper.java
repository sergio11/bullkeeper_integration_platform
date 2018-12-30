package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppInstalledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppRuleDTO;

/**
 * App Installed Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AppInstalledEntityMapper {
	

	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Terminal Repository
	 */
	@Autowired
	protected TerminalRepository terminalRepository;
	
	/**
	 * App Installed Entity to App Installed DTO
	 * @param terminalEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(appInstalledEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(appInstalledEntity.getKid().getId().toString())", target = "kid" ),
        @Mapping(expression="java(appInstalledEntity.getTerminal().getId().toString())",
        		target = "terminalId" ),
        @Mapping(expression="java(appInstalledEntity.getAppRuleEnum().name())", target = "appRule" )
    })
    @Named("appInstalledEntityToAppInstalledDTO")
    public abstract AppInstalledDTO appInstalledEntityToAppInstalledDTO(final AppInstalledEntity appInstalledEntity); 
	
    
    /**
	 * App Installed Entity to App Rule DTO
	 * @param appInstalledEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(appInstalledEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(appInstalledEntity.getAppRuleEnum().name())", target = "appRule" )
    })
    @Named("appInstalledEntityToAppRuleDTO")
    public abstract AppRuleDTO appInstalledEntityToAppRuleDTO(final AppInstalledEntity appInstalledEntity); 
    
    
    /**
     * 
     * @param appInstalledEntitiesList
     * @return
     */
    @IterableMapping(qualifiedByName = "appInstalledEntityToAppRuleDTO")
    public abstract Iterable<AppRuleDTO> appInstalledEntityToAppRuleDTOs(
    		Iterable<AppInstalledEntity> appInstalledEntitiesList);
    
    /**
     * 
     * @param appInstalledEntitiesList
     * @return
     */
    @IterableMapping(qualifiedByName = "appInstalledEntityToAppInstalledDTO")
    public abstract Iterable<AppInstalledDTO> appInstalledEntityToAppInstalledDTO(
    		Iterable<AppInstalledEntity> appInstalledEntitiesList);
    
    
    /**
     * 
     * @param saveAppInstalled
     * @return
     */
    @Mappings({
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveAppInstalled.getKid())))", target="kid"),
    	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(saveAppInstalled.getTerminalId())))", target="terminal"),
    	@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppRuleEnum.valueOf(saveAppInstalled.getAppRule()))", target="appRuleEnum")
    })
    public abstract AppInstalledEntity saveAppInstalledDtoToAppInstalledEntity(final SaveAppInstalledDTO saveAppInstalled);
    
    /**
     * 
     * @param saveAppsInstalled
     * @return
     */
    @IterableMapping(qualifiedByName = "saveAppInstalledDtoToAppInstalledEntity")
    public abstract Iterable<AppInstalledEntity> saveAppInstalledDtoToAppInstalledEntity(final Iterable<SaveAppInstalledDTO> saveAppsInstalled);

   
}
