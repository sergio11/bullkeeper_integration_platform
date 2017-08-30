package sanchez.sanchez.sergio.persistence.repository.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import sanchez.sanchez.sergio.dto.response.CommentsBySonDTO;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.repository.CommentRepositoryCustom;

/**
 *
 * @author sergio
 */
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CommentsBySonDTO> getCommentsBySon() {
        
        TypedAggregation<CommentEntity> commentsAggregation = 
               Aggregation.newAggregation(CommentEntity.class,
                Aggregation.group("sonEntity.firstName").count().as("comments"),
                Aggregation.project("sonEntity.firstName").and("comments").previousOperation());
        
        AggregationResults<CommentsBySonDTO> results = mongoTemplate.
             aggregate(commentsAggregation, CommentsBySonDTO.class);

        List<CommentsBySonDTO> commentsBySonResultsList = results.getMappedResults();

        return commentsBySonResultsList;
    }
    
}
