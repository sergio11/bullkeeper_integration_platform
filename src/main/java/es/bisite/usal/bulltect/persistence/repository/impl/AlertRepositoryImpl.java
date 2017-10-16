package es.bisite.usal.bulltect.persistence.repository.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.repository.AlertRepositoryCustom;
import es.bisite.usal.bulltect.web.dto.response.AlertsBySonDTO;
import es.bisite.usal.bulltect.web.dto.response.CommentsBySonDTO;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
        		new Update().set("delivered", Boolean.TRUE).set("delivered_at", new Date()), AlertEntity.class);
	}

	@Override
	public void setAsDelivered(ObjectId alertId) {
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("_id").in(alertId)),
        		new Update().set("delivered", Boolean.TRUE).set("delivered_at", new Date()), AlertEntity.class);
		
	}

	@Override
	public List<AlertsBySonDTO> getAlertsBySon(List<String> sonIds) {

		
		TypedAggregation<AlertEntity> alertsAggregation = 
				Aggregation.newAggregation(AlertEntity.class,
	                Aggregation.group("son.id", "level").count().as("count"),
	                Aggregation.project().and("son.id").as("id")
	                	.and("alerts").nested(
	                			bind("level", "level").and("count")));
		
		// Aggregation.match(Criteria.where("_id").in(sonIds)
	        
	        AggregationResults<AlertsBySonDTO> results = mongoTemplate.
	             aggregate(alertsAggregation, AlertsBySonDTO.class);

	        List<AlertsBySonDTO> alertsBySonResultsList = results.getMappedResults();

	        return alertsBySonResultsList;
	}   
}
