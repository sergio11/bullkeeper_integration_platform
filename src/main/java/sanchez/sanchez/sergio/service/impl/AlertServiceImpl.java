package sanchez.sanchez.sergio.service.impl;

import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.dto.response.AlertDTO;
import sanchez.sanchez.sergio.mapper.AlertEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.service.IAlertService;

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
	public Page<AlertDTO> findByParentPaginated(ObjectId id, Pageable pageable) {
		Page<AlertEntity> alertsPage = alertRepository.findBySonParentId(id, pageable);
        return alertsPage.map(new Converter<AlertEntity, AlertDTO>(){
            @Override
            public AlertDTO convert(AlertEntity alertEntity) {
               return alertMapper.alertEntityToAlertDTO(alertEntity);
            }
        });
	}
	
	@Override
	public Page<AlertDTO> findByParentPaginated(ObjectId id, AlertLevelEnum level, Pageable pageable) {
		Page<AlertEntity> alertsPage = alertRepository.findByLevelAndSonParentId(level, id, pageable);
        return alertsPage.map(new Converter<AlertEntity, AlertDTO>(){
            @Override
            public AlertDTO convert(AlertEntity alertEntity) {
               return alertMapper.alertEntityToAlertDTO(alertEntity);
            }
        });
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(alertRepository, "Alert Repository cannot be null");
	}
}
