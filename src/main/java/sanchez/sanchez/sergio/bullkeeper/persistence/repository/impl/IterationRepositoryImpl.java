package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.IterationRepositoryCustom;
import sanchez.sanchez.sergio.bullkeeper.persistence.results.IterationAvgAndTotalDuration;
import sanchez.sanchez.sergio.bullkeeper.persistence.results.IterationAvgDuration;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * @author sergio
 */
public class IterationRepositoryImpl implements IterationRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(IterationRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;


	@Override
	public IterationAvgDuration getAvgDuration() {
		
		GroupOperation avgOperation = Aggregation.group()
	            .avg("duration")
	                .as("avg_duration");
		
		Aggregation aggregation = newAggregation(IterationEntity.class, avgOperation);
		
		return mongoTemplate
				  .aggregate(aggregation, IterationEntity.COLLECTION_NAME, IterationAvgDuration.class)
				  .getUniqueMappedResult();
	}

	@Override
	public IterationAvgAndTotalDuration getAvgAndTotalDuration() {
		
		GroupOperation avgOperation = Aggregation.group()
	            .sum("duration")
	                .as("total_duration")
	            .avg("total_duration")
	                .as("avg_duration");
		
		Aggregation aggregation = newAggregation(IterationEntity.class, avgOperation);
		
		return mongoTemplate
				  .aggregate(aggregation, IterationEntity.COLLECTION_NAME, IterationAvgAndTotalDuration.class)
				  .getUniqueMappedResult();
	}

	 
}
