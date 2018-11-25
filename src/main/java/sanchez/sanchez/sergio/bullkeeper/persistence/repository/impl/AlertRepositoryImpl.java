package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

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

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AlertRepositoryCustom;

/**
 * @author sergio
 */
public class AlertRepositoryImpl implements AlertRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(AlertRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 
     */
	@Override
	public void setAsDelivered(final List<ObjectId> alertIds) {
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(alertIds)),
        		new Update().set("delivered", Boolean.TRUE).set("delivered_at", 
        				new Date()), AlertEntity.class);
	}

	/**
	 * 
	 */
	@Override
	public void setAsDelivered(ObjectId alertId) {
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("_id").is(alertId)),
        		new Update().set("delivered", Boolean.TRUE).set("delivered_at", new Date()), AlertEntity.class);
		
	}
	
	@Override
	public Iterable<AlertEntity> findGuardianAlerts(final ObjectId id, final Integer count, 
			final Date from, final AlertLevelEnum[] levels) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		
		final Criteria criteria = Criteria.where("guardian.$id")
				.is(id).and("create_at").gte(from);
		
		if(levels != null && levels.length > 0)
			criteria.andOperator(Criteria.where("level").in(Arrays.asList(levels)));
		
		final Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "create_at"));
		if(count > 0)
			query.limit(count);
        return  mongoTemplate.find(query, AlertEntity.class);
		
	}

	/**
	 * 
	 */
	@Override
	public Iterable<AlertEntity> findKidAlerts(final ObjectId id, final Integer count, 
			final Date from, final AlertLevelEnum[] levels) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		
		final Criteria criteria = Criteria.where("kid.$id")
				.is(id).and("create_at").gte(from);
		
		if(levels != null && levels.length > 0)
			criteria.andOperator(Criteria.where("level").in(Arrays.asList(levels)));
		
		
		final Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "create_at"));
		if(count > 0)
			query.limit(count);
        return  mongoTemplate.find(query, AlertEntity.class);
	}
	
	/**
	 * Find Guardian Alerts By Level
	 * @param id
	 * @param count
	 * @param from
	 * @param level
	 * @return
	 */
	private Iterable<AlertEntity> findGuardianAlertsByLevel(final ObjectId id, final Integer count, 
			final Date from, final AlertLevelEnum level){
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		Assert.notNull(level, "Alert Level can not be null");
		
		final Criteria criteria = Criteria.where("guardian.$id")
				.is(id).and("create_at").gte(from)
				.and("level").is(level);
	
		final Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "create_at"));
		if(count > 0)
			query.limit(count);
        return  mongoTemplate.find(query, AlertEntity.class);
	}

	/**
	 * Find Parent Warning Alerts
	 */
	@Override
	public Iterable<AlertEntity> findGuardianWarningAlerts(final ObjectId id, final Integer count, 
			final Date from) {
		return findGuardianAlertsByLevel(id, count, from, AlertLevelEnum.WARNING);
	}

	/**
	 * Find Parent Information Alerts
	 */
	@Override
	public Iterable<AlertEntity> findGuardianInformationAlerts(ObjectId id, Integer count, Date from) {
		return findGuardianAlertsByLevel(id, count, from, AlertLevelEnum.INFO);
	}

	/**
	 * Find parent success alerts
	 */
	@Override
	public Iterable<AlertEntity> findGuardianSuccessAlerts(ObjectId id, Integer count, Date from) {
		return findGuardianAlertsByLevel(id, count, from, AlertLevelEnum.SUCCESS);
	}

	/**
	 * Find parent danger alerts
	 */
	@Override
	public Iterable<AlertEntity> findGuardianDangerAlerts(ObjectId id, Integer count, Date from) {
		return findGuardianAlertsByLevel(id, count, from, AlertLevelEnum.DANGER);
	}

	/**
	 * Find Son ALerts
	 */
	@Override
	public Iterable<AlertEntity> findKidAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum level) {
		Assert.notNull(id, "Id can not be null");
    	Assert.notNull(count, "Count cannot be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From should be a date before the current time");
		
		final Criteria criteria = Criteria.where("kid.$id")
				.is(id).and("create_at").gte(from)
				.and("level").is(level);
		
		
		final Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "create_at"));
		if(count > 0)
			query.limit(count);
        return  mongoTemplate.find(query, AlertEntity.class);
	}   
}
