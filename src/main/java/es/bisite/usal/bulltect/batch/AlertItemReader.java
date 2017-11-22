package es.bisite.usal.bulltect.batch;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ReaderNotOpenException;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.batch.models.AlertsByParent;
import es.bisite.usal.bulltect.persistence.entity.AlertDeliveryModeEnum;
import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.repository.AlertRepository;
import es.bisite.usal.bulltect.persistence.repository.ParentRepository;

@Component("alertItemReader")
@StepScope
public class AlertItemReader extends AbstractItemCountingItemStreamItemReader<AlertsByParent> {

	private Logger logger = LoggerFactory.getLogger(AlertItemReader.class);
	
	private final ParentRepository parentRepository;
	private final AlertRepository alertRepository;
	
	private List<ParentEntity> parents;
	
	public AlertItemReader(ParentRepository parentRepository, AlertRepository alertRepository) {
		super();
		this.parentRepository = parentRepository;
		this.alertRepository = alertRepository;
		this.setName("alertItemReader");
		this.setExecutionContextName("alertItemReader");
	}
	

	@Override
	protected AlertsByParent doRead() throws Exception {
		if (parents == null) {
            throw new ReaderNotOpenException("Reader must be open before it can be read.");
        }
		logger.debug("AlertItemReader doRead called ...");
		ParentEntity currentParent = parents.get(getCurrentItemCount() - 1);
		List<AlertEntity> alerts = alertRepository
				.findByParentIdAndDeliveredFalseAndDeliveryModeAndCreateAtGreaterThan(currentParent.getId(), AlertDeliveryModeEnum.PUSH_NOTIFICATION, currentParent.getLastAccessToAlerts());
		return new AlertsByParent(currentParent.getId(), alerts);
	}

	@Override
	protected void doOpen() throws Exception {
		logger.debug("AlertItemReader doOpen called ...");
		parents = parentRepository.findByPreferencesPushNotificationsEnabled(Boolean.TRUE);
		setMaxItemCount(parents.size()); 
		
	}

	@Override
	protected void doClose() throws Exception {
		logger.debug("AlertItemReader doClose called ...");
		if(parents != null)
			parents.clear();
	    setMaxItemCount(0);
	    setCurrentItemCount(0);
	}
	



}
