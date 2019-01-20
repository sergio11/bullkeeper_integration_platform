package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppStatsEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppInstalledRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppStatsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppStatsDTO;

/**
 * App Stats Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AppStatsEntityMapper {
	

	/**
	 * App Installed Repository
	 */
	@Autowired
	protected AppInstalledRepository appInstalledRepository;
	
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
	 * App Stats Entity To AppStats DTO
	 */
    @Mappings({
        @Mapping(expression="java(appStatsEntity.getId().toString())", 
        		target = "identity" ),
        @Mapping(source = "appStatsEntity.firstTime", 
        		target = "firstTime", dateFormat = "yyyy/MM/dd HH:mm:ss.SSS"),
        @Mapping(source = "appStatsEntity.lastTime", 
        		target = "lastTime", dateFormat = "yyyy/MM/dd HH:mm:ss.SSS"),
        @Mapping(source = "appStatsEntity.lastTimeUsed", 
        		target = "lastTimeUsed", dateFormat = "yyyy/MM/dd HH:mm:ss.SSS"),
        @Mapping(source = "appStatsEntity.app.packageName", 
        		target = "packageName"),
        @Mapping(expression = "java(appStatsEntity.getKid().getId().toString())", 
				target = "kid"),
        @Mapping(expression = "java(appStatsEntity.getTerminal().getId().toString())", 
				target = "terminal"),
        @Mapping(expression = "java(appStatsEntity.getApp().getId().toString())", target = "app"),
        @Mapping(source = "appStatsEntity.app.appName", target = "appName"),
        @Mapping(source = "appStatsEntity.app.iconEncodedString", target = "iconEncodedString")
    })
    @Named("appStatsEntityToAppStatsDTO")
    public abstract AppStatsDTO appStatsEntityToAppStatsDTO(final AppStatsEntity appStatsEntity); 
	

    /**
     * 
     * @param appStatsEntitiesList
     * @return
     */
    @IterableMapping(qualifiedByName = "appStatsEntityToAppStatsDTO")
    public abstract Iterable<AppStatsDTO> appStatsEntitiesToAppStatsDTO(
    		Iterable<AppStatsEntity> appStatsEntitiesList);
    
    
    /**
     * 
     * @param saveAppStats
     * @return
     */
    @Mappings({
    	@Mapping(expression="java((saveAppStats.getIdentity() != null && !saveAppStats.getIdentity().isEmpty()) "
    			+ "? new org.bson.types.ObjectId(saveAppStats.getIdentity()) : null )", 
    			target="id"),
    	@Mapping(expression="java(appInstalledRepository.findOneByPackageName(saveAppStats.getPackageName()))",
    			target="app"),
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveAppStats.getKid())))", target="kid"),
    	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(saveAppStats.getTerminal())))", target="terminal")
    })
    public abstract AppStatsEntity saveAppStatsDtoToAppStatsEntity(final SaveAppStatsDTO saveAppStats);
    
    /**
     * 
     * @param saveAppStatsList
     * @return
     */
    @IterableMapping(qualifiedByName = "saveAppStatsDtoToAppStatsEntity")
    public abstract Iterable<AppStatsEntity> saveAppStatsDtoToAppStatsEntity(final Iterable<SaveAppStatsDTO> saveAppStatsList);

   
}
