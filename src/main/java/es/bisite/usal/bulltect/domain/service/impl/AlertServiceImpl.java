package es.bisite.usal.bulltect.domain.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.mapper.AlertEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.AlertCategoryEnum;
import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.repository.AlertRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.web.dto.request.AddAlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertsStatisticsDTO.AlertLevelDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertsPageDTO;

@Service
public class AlertServiceImpl implements IAlertService {
	
	private static Logger logger = LoggerFactory.getLogger(AlertServiceImpl.class);

    private final AlertRepository alertRepository;
    private final AlertEntityMapper alertMapper;
    private final IMessageSourceResolverService messageSourceResolverService;
    private final SonRepository sonRepository;

    public AlertServiceImpl(AlertRepository alertRepository, AlertEntityMapper alertMapper,
            IMessageSourceResolverService messageSourceResolverService, SonRepository sonRepository) {
        super();
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
        this.messageSourceResolverService = messageSourceResolverService;
        this.sonRepository = sonRepository;
    }
    
    @Override
    public AlertDTO findById(ObjectId id) {
        return alertMapper.alertEntityToAlertDTO(alertRepository.findOne(id));
    }

    @Override
    public Page<AlertDTO> findByParentPaginated(ObjectId id, Boolean delivered, Pageable pageable) {
        Page<AlertEntity> alertsPage = alertRepository.findByParentIdAndDeliveredOrderByCreateAtDesc(id, delivered, pageable);
        return alertsPage.map(new Converter<AlertEntity, AlertDTO>() {
            @Override
            public AlertDTO convert(AlertEntity alertEntity) {
                return alertMapper.alertEntityToAlertDTO(alertEntity);
            }
        });
    }

    @Override
    public Page<AlertDTO> findByParentPaginated(ObjectId id, AlertLevelEnum level, Boolean delivered, Pageable pageable) {
        Page<AlertEntity> alertsPage = alertRepository.findByLevelAndParentIdAndDeliveredOrderByCreateAtDesc(level, id, delivered, pageable);
        return alertsPage.map(new Converter<AlertEntity, AlertDTO>() {
            @Override
            public AlertDTO convert(AlertEntity alertEntity) {
                return alertMapper.alertEntityToAlertDTO(alertEntity);
            }
        });
    }

    @Override
    public AlertDTO save(AddAlertDTO alert) {
        final AlertEntity alertToSave = alertMapper.addAlertDTOToAlertEntity(alert);
        final AlertEntity alertSaved = alertRepository.save(alertToSave);
        return alertMapper.alertEntityToAlertDTO(alertSaved);
    }

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

    @Override
    public Long getTotalAlerts() {
        return alertRepository.count();
    }
    
    @Override
	public Iterable<AlertDTO> findByParent(ObjectId id, Boolean delivered) {
    	List<AlertEntity> alertEntities = alertRepository.findByDeliveredTrueAndParentIdOrderByCreateAtDesc(id);
    	return alertMapper.alertEntitiesToAlertDTO(alertEntities);
	}
    
    @Override
	public AlertsPageDTO getLastAlerts(ObjectId parent, Date lastAccessToAlerts, Integer count, String[] levels) {
    	
    	AlertsPageDTO alertsPageDTO = new AlertsPageDTO();
		
    	Pageable countAlerts = new PageRequest(0, count);
		Iterable<AlertEntity> lastAlerts = levels != null && levels.length > 0 ? 
				alertRepository.findByParentIdAndCreateAtGreaterThanEqualAndLevelIsInOrderByCreateAtDesc(parent, lastAccessToAlerts, levels, countAlerts) :
					alertRepository.findByParentIdAndCreateAtGreaterThanEqualOrderByCreateAtDesc(parent, lastAccessToAlerts, countAlerts);
		Iterable<AlertDTO> lastAlertsDTO = alertMapper.alertEntitiesToAlertDTO(lastAlerts);
		alertsPageDTO.setAlerts(lastAlertsDTO);
		// total alerts count
		Integer totalAlerts = alertRepository.countByParentId(parent);
		alertsPageDTO.setTotal(totalAlerts);
		// total returned alerts count
		Integer returned = Iterables.size(lastAlerts);
		alertsPageDTO.setReturned(returned);
		// total remaining alerts
		Integer totalNewAlerts = alertRepository.countByParentIdAndCreateAtGreaterThanEqual(parent, lastAccessToAlerts);
		alertsPageDTO.setRemaining(totalNewAlerts - returned);
		alertsPageDTO.setLastQuery(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(lastAccessToAlerts));
		
		return alertsPageDTO;
		
	}
        
        
    @Override
    public Long deleteAlertsOfParent(ObjectId parent) {
        return alertRepository.deleteByParentId(parent);
    }
    
    @Override
    public Iterable<AlertDTO> findBySon(ObjectId son) {
       return this.alertMapper.alertEntitiesToAlertDTO(alertRepository.findBySonIdOrderByCreateAtDesc(son));
    }
    
    @Override
    public Long clearChildAlerts(ObjectId son) {
        return alertRepository.deleteBySonId(son);
    }
    
    @Override
    public void deleteById(ObjectId id) {
        alertRepository.delete(id);
    }
    
    @Override
    public void createInvalidAccessTokenAlert(String payload, ParentEntity parent, SonEntity son) {
        Assert.notNull(payload, "Payload can not be null");
        Assert.hasLength(payload, "Payload can not be empty");
        Assert.notNull(parent, "Parent can not be null");
        Assert.notNull(son, "Son can not be null");
        String title = messageSourceResolverService.resolver("alerts.title.invalid.access.token", parent.getLocale());
        alertRepository.save(new AlertEntity(AlertLevelEnum.WARNING, title, payload, parent, son, AlertCategoryEnum.INFORMATION_SON));
    }
    
    @Override
	public Iterable<AlertDTO> findByParent(ObjectId id) {
    	return alertMapper.alertEntitiesToAlertDTO(alertRepository.findByParentIdOrderByCreateAtDesc(id));
	}
    
    @Override
	public AlertsStatisticsDTO getAlertsStatistics(ObjectId parentId, List<ObjectId> sonIds, Date from) {
    	
    	
    	Map<AlertLevelEnum, Long> alertsByLevel = 
    			(sonIds == null || sonIds.isEmpty() ? 
    					alertRepository.findByParentIdAndCreateAtGreaterThanEqual(parentId, from) :
    						alertRepository.findByParentIdAndSonIdInAndCreateAtGreaterThanEqual(parentId, sonIds, from)	)
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
    
    @Override
	public AlertDTO save(AlertLevelEnum level, String title, String payload, ObjectId sonId) {
    	return save(level, title, payload, sonId, AlertCategoryEnum.DEFAULT);
	}
    
    @Override
	public AlertDTO save(AlertLevelEnum level, String title, String payload, ObjectId sonId,
			AlertCategoryEnum category) {
    	Assert.notNull(level, "Level can not be null");
    	Assert.notNull(title, "Title can not be null");
    	Assert.hasLength(title, "Title can not be empty");
    	Assert.notNull(payload, "Payload can not be null");
    	Assert.hasLength(payload, "Payload can not be empty");
    	Assert.notNull(sonId, "Son can not be null");
    	Assert.notNull(category, "category can not be null");
    	
    	
    	final SonEntity target = sonRepository.findOne(sonId);
    	final AlertEntity alertToSave = new AlertEntity(level, title, payload, target.getParent(), target, category);
    	final AlertEntity alertSaved = alertRepository.save(alertToSave);
        return alertMapper.alertEntityToAlertDTO(alertSaved);
	}

    @PostConstruct
    protected void init() {
        Assert.notNull(alertRepository, "Alert Repository cannot be null");
        Assert.notNull(alertMapper, "Alert Mapper cannot be null");
        Assert.notNull(messageSourceResolverService, "Message Source Resolver Service cannot be null");
        
    }

	
}
