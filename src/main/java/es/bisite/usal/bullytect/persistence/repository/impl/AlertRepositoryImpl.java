package es.bisite.usal.bullytect.persistence.repository.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import es.bisite.usal.bullytect.persistence.entity.AlertEntity;
import es.bisite.usal.bullytect.persistence.repository.AlertRepositoryCustom;

/**
 * @author sergio
 */
public class AlertRepositoryImpl implements AlertRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(AlertRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

	@Override
	public void setAsDelivered(List<ObjectId> alertIds) {
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(alertIds)),
        		Update.update("delivered", "true").update("delivered_at", new Date()), AlertEntity.class);
	}

	@Override
	public void setAsDelivered(ObjectId alertId) {
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("_id").in(alertId)),
        		Update.update("delivered", "true").update("delivered_at", new Date()), AlertEntity.class);
		
	}   
}
