package es.bisite.usal.bulltect.batch;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ReaderNotOpenException;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.batch.models.AlertsByParent;
import es.bisite.usal.bulltect.persistence.entity.AlertDeliveryModeEnum;
import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.repository.AlertRepository;
import es.bisite.usal.bulltect.persistence.repository.ParentRepository;

@Component("alertItemReader")
@StepScope
public class AlertItemReader extends AbstractItemCountingItemStreamItemReader<AlertsByParent> {

	private Logger logger = LoggerFactory.getLogger(AlertItemReader.class);
	
	private final ParentRepository parentRepository;
	private final AlertRepository alertRepository;
	
	private List<ObjectId> parentIds;
	
	public AlertItemReader(ParentRepository parentRepository, AlertRepository alertRepository) {
		super();
		this.parentRepository = parentRepository;
		this.alertRepository = alertRepository;
		
		this.setName("alertItemReader");
		this.setExecutionContextName("alertItemReader");
	}
	

	@Override
	protected AlertsByParent doRead() throws Exception {
		if (parentIds == null) {
            throw new ReaderNotOpenException("Reader must be open before it can be read.");
        }
		logger.debug("AlertItemReader doRead called ...");
		ObjectId currentParent = parentIds.get(getCurrentItemCount() - 1);
		List<AlertEntity> alerts = alertRepository.findByParentIdAndDeliveredFalseAndDeliveryMode(currentParent, AlertDeliveryModeEnum.PUSH_NOTIFICATION);
		return new AlertsByParent(currentParent, alerts);
	}

	@Override
	protected void doOpen() throws Exception {
		logger.debug("AlertItemReader doOpen called ...");
		parentIds = parentRepository.getParentIds();
		setMaxItemCount(parentIds.size());
		logger.debug("Total Parent Ids -> " + parentIds.size()); 
		
	}

	@Override
	protected void doClose() throws Exception {
		logger.debug("AlertItemReader doClose called ...");
		parentIds.clear();
	    setMaxItemCount(0);
	    setCurrentItemCount(0);
	}
	



}
