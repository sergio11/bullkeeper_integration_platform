package es.bisite.usal.bulltect.domain.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.mapper.AlertEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.repository.AlertRepository;
import es.bisite.usal.bulltect.web.dto.request.AddAlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertDTO;

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

    @PostConstruct
    protected void init() {
        Assert.notNull(alertRepository, "Alert Repository cannot be null");
    }
}
