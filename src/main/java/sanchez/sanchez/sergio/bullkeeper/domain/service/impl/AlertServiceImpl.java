package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.google.common.collect.Iterables;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.mapper.AlertEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.util.Utils;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddAlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertsPageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertsStatisticsDTO.AlertLevelDTO;


/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Service
public class AlertServiceImpl implements IAlertService {
	
	private static Logger logger = LoggerFactory.getLogger(AlertServiceImpl.class);

    private final AlertRepository alertRepository;
    private final AlertEntityMapper alertMapper;
    private final IMessageSourceResolverService messageSourceResolverService;
    private final KidRepository kidRepository;
    private final GuardianRepository guardianRepository;
    private final SupervisedChildrenRepository supervisedChildrenRepository;

    /**
     * 
     * @param alertRepository
     * @param alertMapper
     * @param messageSourceResolverService
     * @param kidRepository
     * @param supervisedChildrenRepository
     * @param guardianRepository
     */
    public AlertServiceImpl(AlertRepository alertRepository, AlertEntityMapper alertMapper,
            IMessageSourceResolverService messageSourceResolverService, 
            final KidRepository kidRepository, 
            final SupervisedChildrenRepository supervisedChildrenRepository,
            final GuardianRepository guardianRepository) {
        super();
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
        this.messageSourceResolverService = messageSourceResolverService;
        this.kidRepository = kidRepository;
        this.supervisedChildrenRepository = supervisedChildrenRepository;
        this.guardianRepository = guardianRepository;
    }
    
    /**
     * 
     */
    @Override
    public AlertDTO findById(final ObjectId id) {
        return alertMapper.alertEntityToAlertDTO(alertRepository.findOne(id));
    }

    /**
     * 
     */
    @Override
    public Page<AlertDTO> findByGuardianPaginated(ObjectId id, Boolean delivered, Pageable pageable) {
        Page<AlertEntity> alertsPage = alertRepository.findByGuardianIdAndDeliveredOrderByCreateAtDesc(id, delivered, pageable);
        return alertsPage.map(new Converter<AlertEntity, AlertDTO>() {
            @Override
            public AlertDTO convert(AlertEntity alertEntity) {
                return alertMapper.alertEntityToAlertDTO(alertEntity);
            }
        });
    }

    /**
     * 
     */
    @Override
    public Page<AlertDTO> findByGuardianPaginated(ObjectId id, AlertLevelEnum level, Boolean delivered, Pageable pageable) {
        Page<AlertEntity> alertsPage = alertRepository.findByLevelAndGuardianIdAndDeliveredOrderByCreateAtDesc(level, id, delivered, pageable);
        return alertsPage.map(new Converter<AlertEntity, AlertDTO>() {
            @Override
            public AlertDTO convert(AlertEntity alertEntity) {
                return alertMapper.alertEntityToAlertDTO(alertEntity);
            }
        });
    }

    /**
     * 
     */
    @Override
    public void save(AddAlertDTO alert) {
        final AlertEntity alertToSave = alertMapper.addAlertDTOToAlertEntity(alert);
        alertRepository.save(alertToSave);
    }

    /**
     * 
     */
    @Override
    public Page<AlertDTO> findPaginated(Boolean delivered, Pageable pageable) {
        Page<AlertEntity> alertsPage = alertRepository.findAll(pageable);
        return alertsPage.map(new Converter<AlertEntity, AlertDTO>() {
            @Override
            public AlertDTO convert(AlertEntity a) {
                return alertMapper.alertEntityToAlertDTO(a);
            }
        });
    }

    /**
     * 
     */
    @Override
    public Long getTotalAlerts() {
        return alertRepository.count();
    }
    
    /**
     * 
     */
    @Override
	public Iterable<AlertDTO> findByGuardian(ObjectId id, Boolean delivered) {
    	List<AlertEntity> alertEntities = alertRepository.findByDeliveredTrueAndGuardianIdOrderByCreateAtDesc(id);
    	return alertMapper.alertEntitiesToAlertDTO(alertEntities);
	}
    
    /**
     * 
     */
    @Override
	public AlertsPageDTO getLastAlerts(ObjectId guardian, Date lastAccessToAlerts, Integer count, String[] levels) {
    	
    	AlertsPageDTO alertsPageDTO = new AlertsPageDTO();
		
    	Pageable countAlerts = new PageRequest(0, count);
		Iterable<AlertEntity> lastAlerts = levels != null && levels.length > 0 ? 
				alertRepository.findByGuardianIdAndCreateAtGreaterThanEqualAndLevelIsInOrderByCreateAtDesc(guardian, lastAccessToAlerts, levels, countAlerts) :
					alertRepository.findByGuardianIdAndCreateAtGreaterThanEqualOrderByCreateAtDesc(guardian, lastAccessToAlerts, countAlerts);
		Iterable<AlertDTO> lastAlertsDTO = alertMapper.alertEntitiesToAlertDTO(lastAlerts);
		alertsPageDTO.setAlerts(lastAlertsDTO);
		// total alerts count
		Integer totalAlerts = alertRepository.countByGuardianId(guardian);
		alertsPageDTO.setTotal(totalAlerts);
		// total returned alerts count
		Integer returned = Iterables.size(lastAlerts);
		alertsPageDTO.setReturned(returned);
		// total remaining alerts
		Integer totalNewAlerts = alertRepository.countByGuardianIdAndCreateAtGreaterThanEqual(guardian, lastAccessToAlerts);
		alertsPageDTO.setRemaining(Math.abs(totalNewAlerts - returned));
		alertsPageDTO.setLastQuery(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(lastAccessToAlerts));
		return alertsPageDTO;
		
	}
    
    /**
     * 
     */
    @Override
	public AlertsPageDTO getLastAlerts(ObjectId guardian, Integer count, String[] levels) {
    	AlertsPageDTO alertsPageDTO = new AlertsPageDTO();
		
    	Pageable countAlerts = new PageRequest(0, count);
		Iterable<AlertEntity> lastAlerts = levels != null && levels.length > 0 ? 
				alertRepository.findByGuardianIdAndLevelIsInOrderByCreateAtDesc(guardian, levels, countAlerts) :
					alertRepository.findByGuardianIdOrderByCreateAtDesc(guardian, countAlerts);
		Iterable<AlertDTO> lastAlertsDTO = alertMapper.alertEntitiesToAlertDTO(lastAlerts);
		alertsPageDTO.setAlerts(lastAlertsDTO);
		// total alerts count
		Integer totalAlerts = alertRepository.countByGuardianId(guardian);
		alertsPageDTO.setTotal(totalAlerts);
		// total returned alerts count
		Integer returned = Iterables.size(lastAlerts);
		alertsPageDTO.setReturned(returned);
		alertsPageDTO.setRemaining(0);
		alertsPageDTO.setLastQuery(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		return alertsPageDTO;
	}
        
   /**
    *      
    */
    @Override
    public Long deleteAlertsOfGuardian(ObjectId id) {
        return alertRepository.deleteByGuardianId(id);
    }
    
    
    /**
     * @param kid
     * @param guardian
     */
    @Override
    public Iterable<AlertDTO> findByKidAndGuardian(final ObjectId kid, final ObjectId guardian) {
       return this.alertMapper.alertEntitiesToAlertDTO(alertRepository
    		   .findByKidIdAndGuardianIdOrderByCreateAtDesc(kid, guardian));
    }
    
    /**
     * 
     */
    @Override
    public Long clearKidAlerts(ObjectId id) {
        return alertRepository.deleteByKidId(id);
    }
    
    /**
     * 
     */
    @Override
    public void deleteById(ObjectId id) {
        alertRepository.delete(id);
    }
    
    /**
     * 
     */
    @Override
    public void createInvalidAccessTokenAlert(String payload, KidEntity kid) {
        Assert.notNull(payload, "Payload can not be null");
        Assert.hasLength(payload, "Payload can not be empty");
        Assert.notNull(kid, "Son can not be null");
        
        String title = messageSourceResolverService.resolver("alerts.title.invalid.access.token", Locale.getDefault());
        save(AlertLevelEnum.WARNING, title, payload, 
        		kid.getId(), AlertCategoryEnum.INFORMATION_KID);
       
    }
    
    /**
     * 
     */
    @Override
	public Iterable<AlertDTO> findByGuardian(ObjectId id) {
    	return alertMapper.alertEntitiesToAlertDTO(
    			alertRepository.findByGuardianIdOrderByCreateAtDesc(id));
	}
    
    /**
     * 
     */
    @Override
	public AlertsStatisticsDTO getAlertsStatistics(ObjectId guardian, List<ObjectId> kidIds, Date from) {
    	
    	
    	Map<AlertLevelEnum, Long> alertsByLevel = 
    			(kidIds == null || kidIds.isEmpty() ? 
    					alertRepository.findByGuardianIdAndCreateAtGreaterThanEqual(guardian, from) :
    						alertRepository.findByGuardianIdAndKidIdInAndCreateAtGreaterThanEqual(guardian, kidIds, from)	)
    		.parallelStream()
    		.collect(Collectors.groupingBy(AlertEntity::getLevel, Collectors.counting()));
    	
    	
    	
    	final Integer totalAlerts = alertsByLevel.values().stream().mapToInt(Number::intValue).sum();

    	List<AlertLevelDTO> alertsData = alertsByLevel.entrySet()
    		.stream()
    		.map(alertByLevelEntry -> new AlertLevelDTO(
    				alertByLevelEntry.getKey(), 
    				(long)Math.round(alertByLevelEntry.getValue().floatValue()/totalAlerts.floatValue()*100),
    				Math.round(alertByLevelEntry.getValue().floatValue()/totalAlerts.floatValue()*100) + "%"))
    		.collect(Collectors.toList());
    	
    	PrettyTime pt = new PrettyTime(LocaleContextHolder.getLocale());
    	
		return new AlertsStatisticsDTO(
				messageSourceResolverService.resolver("statistics.alerts.title", new Object[] { pt.format(from) }), 
				alertsData);
    	
	}
    
    /**
     * 
     */
    @Override
	public Map<AlertLevelEnum, Long> getTotalAlertsByKidAndGuardianId(final ObjectId kid, final ObjectId guardian) {
    	Assert.notNull(kid, "Kid id can not be null");
    	Assert.notNull(guardian, "Guardian can not be null");
		
    	final Date from = Utils.getDateNDaysAgo(1);
    	
    	Map<AlertLevelEnum, Long> alertsByLevel = alertRepository.findByKidIdAndGuardianIdAndCreateAtGreaterThanEqual(kid, guardian, from)	
    		.parallelStream()
    		.collect(Collectors.groupingBy(AlertEntity::getLevel, Collectors.counting()));
    	
    	
    	return alertsByLevel;
	}
    
    /**
     * 
     */
    @Override
	public void save(AlertLevelEnum level, String title, String payload, ObjectId kid) {
    	save(level, title, payload, kid, AlertCategoryEnum.DEFAULT);
	}
    
    /**
     * 
     */
    @Override
	public void save(AlertLevelEnum level, String title, String payload, ObjectId kid,
			AlertCategoryEnum category) {
    	Assert.notNull(level, "Level can not be null");
    	Assert.notNull(title, "Title can not be null");
    	Assert.hasLength(title, "Title can not be empty");
    	Assert.notNull(payload, "Payload can not be null");
    	Assert.hasLength(payload, "Payload can not be empty");
    	Assert.notNull(kid, "Son can not be null");
    	Assert.notNull(category, "category can not be null");
    	
    	// Find Kid Entity
    	final KidEntity target = kidRepository.findOne(kid);
    	
    	// Get Supervised Children
    	final List<SupervisedChildrenEntity> supervisedChildrenEntities = 
    			supervisedChildrenRepository.findByKidIdAndIsConfirmed(target.getId(), true);
    	
    	
    	final List<AlertEntity> alertsToSave = supervisedChildrenEntities.stream()
    			.map(supervisedChildren -> new AlertEntity(level, title, payload, 
    						supervisedChildren.getGuardian(), 
    						supervisedChildren.getKid(), category))
    			.collect(Collectors.toList());
    
    	// Save Alerts
    	alertRepository.save(alertsToSave);
    
	}
    
    /**
     * 
     */
    @Override
	public void save(AlertLevelEnum level, String title, String payload, ObjectId kid, ObjectId guardian,
			AlertCategoryEnum category) {
    	Assert.notNull(level, "Level can not be null");
    	Assert.notNull(title, "Title can not be null");
    	Assert.hasLength(title, "Title can not be empty");
    	Assert.notNull(payload, "Payload can not be null");
    	Assert.hasLength(payload, "Payload can not be empty");
    	Assert.notNull(kid, "Kid can not be null");
    	Assert.notNull(guardian, "Guardian can not be null");
    	Assert.notNull(category, "category can not be null");
    	
    	
    	// Find Kid Entity
    	final KidEntity kidEntity = kidRepository.findOne(kid);
    	// Find Guardian Entity
    	final GuardianEntity guardianEntity = guardianRepository.findOne(guardian);
    	// Create alert to save
    	final AlertEntity alertToSave = new AlertEntity(level, title, payload, guardianEntity, kidEntity, category);
    	// Save alert
    	alertRepository.save(alertToSave);
    	
	}
    
    /**
     * 
     */
    @Override
	public Iterable<AlertDTO> findGuardianAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels) {
    	Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
    	return alertMapper.alertEntitiesToAlertDTO(alertRepository.findGuardianAlerts(id, count, from, levels));
	}

    /**
     * 
     */
	@Override
	public Iterable<AlertDTO> findKidAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		return alertMapper.alertEntitiesToAlertDTO(alertRepository.findKidAlerts(id, count, from, levels));
	}

    
	/**
	 * Find Parent Warning Alerts
	 */
	@Override
	public Iterable<AlertDTO> findGuardianWarningAlerts(final ObjectId id, final Integer count, final Date from) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
    	return alertMapper.alertEntitiesToAlertDTO(alertRepository.findGuardianWarningAlerts(id, count, from));
	}

	/**
	 * Find Guardian Danger Alerts
	 */
	@Override
	public Iterable<AlertDTO> findGuardianDangerAlerts(final ObjectId id, final Integer count, final Date from) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
    	return alertMapper.alertEntitiesToAlertDTO(alertRepository.findGuardianDangerAlerts(id, count, from));
	}

	/**
	 * Find Guardian Information Alerts
	 */
	@Override
	public Iterable<AlertDTO> findGuardianInformationAlerts(final ObjectId id, final Integer count, final Date from) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
    	return alertMapper.alertEntitiesToAlertDTO(alertRepository.findGuardianInformationAlerts(id, count, from));
	}

	/**
	 * Find GUardian Success Alerts
	 */
	@Override
	public Iterable<AlertDTO> findGuardianSuccessAlerts(final ObjectId id, final Integer count, final Date from) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
    	return alertMapper.alertEntitiesToAlertDTO(alertRepository.findGuardianSuccessAlerts(id, count, from));
	}
	
	/**
	 * Delete WArning Alerts Of Guardian
	 */
	@Override
	public Long deleteWarningAlertsOfGuardian(ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		return alertRepository.deleteByGuardianIdAndLevel(guardian, AlertLevelEnum.WARNING);
	}

	/**
	 * Delete Info ALerts Of Guardian
	 */
	@Override
	public Long deleteInfoAlertsOfGuardian(final ObjectId guardian) {
		Assert.notNull(guardian, "Parent can not be null");
		return alertRepository.deleteByGuardianIdAndLevel(guardian, AlertLevelEnum.INFO);
	}

	/**
	 * Delete Danger Alerts Of Guardian
	 */
	@Override
	public Long deleteDangerAlertsOfGuardian(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		return alertRepository.deleteByGuardianIdAndLevel(guardian, AlertLevelEnum.DANGER);
	}

	/**
	 * Delete Success Alerts Of Guardian
	 */
	@Override
	public Long deleteSuccessAlertsOfGuardian(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		return alertRepository.deleteByGuardianIdAndLevel(guardian, AlertLevelEnum.SUCCESS);
	}
	
	/**
	 * Find Kid WArning Alerts
	 */
	@Override
	public Iterable<AlertDTO> findKidWarningAlerts(ObjectId id, Integer count, Date from) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		return alertMapper.alertEntitiesToAlertDTO(alertRepository
				.findKidAlerts(id, count, from, AlertLevelEnum.WARNING));
	}

	/**
	 * Find Information Kid Alerts
	 */
	@Override
	public Iterable<AlertDTO> findInformationKidAlerts(ObjectId id, Integer count, Date from) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		return alertMapper.alertEntitiesToAlertDTO(alertRepository.findKidAlerts(id, count, from, AlertLevelEnum.INFO));
	}

	
	/**
	 * Find Danger Kid Alerts
	 */
	@Override
	public Iterable<AlertDTO> findDangerKidAlerts(ObjectId id, Integer count, Date from) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		return alertMapper.alertEntitiesToAlertDTO(alertRepository
				.findKidAlerts(id, count, from, AlertLevelEnum.DANGER));
	}

	/**
	 * Find Success Kid Alerts
	 */
	@Override
	public Iterable<AlertDTO> findSuccessKidAlerts(ObjectId id, Integer count, Date from) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		return alertMapper.alertEntitiesToAlertDTO(alertRepository
				.findKidAlerts(id, count, from, AlertLevelEnum.SUCCESS));
	}
	
	/**
	 * Clear Kid Alerts By Level
	 */
	@Override
	public Long clearKidAlertsByLevel(ObjectId id, AlertLevelEnum level) {
		return alertRepository.deleteByKidIdAndLevel(id, level);
	}
	
	@PostConstruct
    protected void init() {
        Assert.notNull(alertRepository, "Alert Repository cannot be null");
        Assert.notNull(alertMapper, "Alert Mapper cannot be null");
        Assert.notNull(messageSourceResolverService, "Message Source Resolver Service cannot be null");
        
    }
	
}
