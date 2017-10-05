package es.bisite.usal.bulltect.domain.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Iterables;

import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.mapper.AlertEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.repository.AlertRepository;
import es.bisite.usal.bulltect.web.dto.request.AddAlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertsPageDTO;

@Service
public class AlertServiceImpl implements IAlertService {

    private final AlertRepository alertRepository;
    private final AlertEntityMapper alertMapper;

    public AlertServiceImpl(AlertRepository alertRepository, AlertEntityMapper alertMapper) {
        super();
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
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
	public AlertsPageDTO getAlerts(ObjectId parent, Date lastAccessToAlerts, Integer count) {
    	
    	AlertsPageDTO alertsPageDTO = new AlertsPageDTO();
		
    	Pageable countAlerts = new PageRequest(0, count);
		Iterable<AlertEntity> lastAlerts = alertRepository.findByParentIdAndCreateAtGreaterThanEqualOrderByCreateAtDesc(parent, lastAccessToAlerts, countAlerts);
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
		alertsPageDTO.setLastQuery(new SimpleDateFormat("yyyy-MM-dd").format(lastAccessToAlerts));
		
		return alertsPageDTO;
		
	}

    @PostConstruct
    protected void init() {
        Assert.notNull(alertRepository, "Alert Repository cannot be null");
    }

	
}
