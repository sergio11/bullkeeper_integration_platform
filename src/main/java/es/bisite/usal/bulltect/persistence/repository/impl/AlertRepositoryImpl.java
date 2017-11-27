package es.bisite.usal.bulltect.persistence.repository.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.repository.AlertRepositoryCustom;
import io.jsonwebtoken.lang.Assert;

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
        		new Query(Criteria.where("_id").is(alertId)),
        		new Update().set("delivered", Boolean.TRUE).set("delivered_at", new Date()), AlertEntity.class);
		
	}
	
	@Override
	public Iterable<AlertEntity> findParentAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		
		final Criteria criteria = Criteria.where("parent.$id")
				.is(id).and("create_at").gte(from);
		
		if(levels != null && levels.length > 0)
			criteria.andOperator(Criteria.where("level").in(Arrays.asList(levels)));
		
		final Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "create_at"));
		if(count > 0)
			query.limit(count);
        return  mongoTemplate.find(query, AlertEntity.class);
		
	}

	@Override
	public Iterable<AlertEntity> findSonAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		
		final Criteria criteria = Criteria.where("son.$id")
				.is(id).and("create_at").gte(from);
		
		if(levels != null && levels.length > 0)
			criteria.andOperator(Criteria.where("level").in(Arrays.asList(levels)));
		
		
		final Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "create_at"));
		if(count > 0)
			query.limit(count);
        return  mongoTemplate.find(query, AlertEntity.class);
	}   
}
