package sanchez.sanchez.sergio.persistence.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import sanchez.sanchez.sergio.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.persistence.repository.IterationRepositoryCustom;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * @author sergio
 */
public class IterationRepositoryImpl implements IterationRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(IterationRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

	@Override
	public Long getAvgDuration() {
		
		GroupOperation avgOperation = Aggregation.group()
			.sum("duration")
				.as("total_duration")
			.avg("total_duration")
				.as("avg_duration");
		
		Aggregation aggregation = newAggregation(IterationEntity.class, avgOperation);
		
		return mongoTemplate.aggregate(aggregation, IterationEntity.COLLECTION_NAME, Long.class).getUniqueMappedResult();
			
	}

	 
}
