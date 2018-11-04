package sanchez.sanchez.sergio.masoc.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.masoc.persistence.entity.AppInstalledEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.ITerminalRepository;
import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.AppInstalledDTO;

/**
 * App Installed Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AppInstalledEntityMapper {
	

	/**
	 * Son Repository
	 */
	@Autowired
	protected SonRepository sonRepository;
	
	/**
	 * Terminal REpository
	 */
	@Autowired
	protected ITerminalRepository terminalRepository;
	
	/**
	 * App Installed Entity to App Installed DTO
	 * @param terminalEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(appInstalledEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(appInstalledEntity.getSon().getId().toString())", target = "sonId" ),
        @Mapping(expression="java(appInstalledEntity.getTerminal().getId().toString())", target = "terminalId" ),
        @Mapping(expression="java(appInstalledEntity.getAppRuleEnum().name())", target = "appRule" )
    })
    @Named("appInstalledEntityToAppInstalledDTO")
    public abstract AppInstalledDTO appInstalledEntityToAppInstalledDTO(final AppInstalledEntity appInstalledEntity); 
	
    /**
     * 
     * @param appInstalledEntitiesList
     * @return
     */
    @IterableMapping(qualifiedByName = "appInstalledEntityToAppInstalledDTO")
    public abstract Iterable<AppInstalledDTO> appInstalledEntityToAppInstalledDTO(Iterable<AppInstalledEntity> appInstalledEntitiesList);
    
    
    /**
     * 
     * @param saveAppInstalled
     * @return
     */
    @Mappings({
    	@Mapping(expression="java((saveAppInstalled.getIdentity() != null && !saveAppInstalled.getIdentity().isEmpty()) ? new org.bson.types.ObjectId(saveAppInstalled.getIdentity()) : null )", target="id"),
    	@Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(saveAppInstalled.getSonId())))", target="son"),
    	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(saveAppInstalled.getTerminalId())))", target="terminal"),
    	@Mapping(expression="java(sanchez.sanchez.sergio.masoc.persistence.entity.AppRuleEnum.valueOf(saveAppInstalled.getAppRule()))", target="appRuleEnum")
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
