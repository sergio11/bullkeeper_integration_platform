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

import com.mongodb.BasicDBObject;

import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.repository.AlertRepositoryCustom;
import es.bisite.usal.bulltect.web.dto.response.AlertsStatisticsDTO;
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
	public List<AlertsStatisticsDTO> getAlertsBySon(List<String> sonIds) {

		
		TypedAggregation<AlertEntity> alertsAggregation = 
				Aggregation.newAggregation(AlertEntity.class,
						unwind("$son"),
						Aggregation.group("$son", "$level").count().as("count"),
						Aggregation.group("$_id.son")
							.addToSet(new BasicDBObject("level", "$_id.level").append("count", "$count")).as("alerts"));
		
		// Aggregation.match(Criteria.where("_id").in(sonIds)
	        
	        AggregationResults<AlertsStatisticsDTO> results = mongoTemplate.
	             aggregate(alertsAggregation, AlertsStatisticsDTO.class);

	        List<AlertsStatisticsDTO> alertsBySonResultsList = results.getMappedResults();

	        return alertsBySonResultsList;
	}   
}
