package es.bisite.usal.bulltect.persistence.repository.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.CommentStatusEnum;
import es.bisite.usal.bulltect.persistence.repository.CommentRepositoryCustom;
import es.bisite.usal.bulltect.web.dto.response.CommentsBySonDTO;

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

	@Override
	public void updateCommentStatus(List<ObjectId> ids, CommentStatusEnum status) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notNull(status, "Status can not be null");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("status", status), CommentEntity.class);
		
	}

	@Override
	public void cancelCommentsInprogress() {
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("status").in(CommentStatusEnum.IN_PROGRESS)),
        		new Update().set("status", CommentStatusEnum.PENDING), CommentEntity.class);
		
	}
    
}
