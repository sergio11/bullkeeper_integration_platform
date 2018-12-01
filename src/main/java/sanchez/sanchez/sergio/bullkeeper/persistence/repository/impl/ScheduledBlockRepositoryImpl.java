package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ScheduledBlockRepositoryCustom;

/*
 * Scheduled Block Repository Impl
 */
public class ScheduledBlockRepositoryImpl implements ScheduledBlockRepositoryCustom {

	private static Logger logger = LoggerFactory.getLogger(ScheduledBlockRepositoryImpl.class);
    
	/**
	 * Mongo Template
	 */
    @Autowired
    private MongoTemplate mongoTemplate;
	
	/**
	 * Enable Scheduled Blocks
	 */
	@Override
	public void enableScheduledBlocks(final Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		Update.update("enable", true), 
        			ScheduledBlockEntity.class);
	
	}

	/**
	 * Disable Scheduled Blocks
	 */
	@Override
	public void disableScheduledBlocks(final Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
	
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		Update.update("enable", false), 
        			ScheduledBlockEntity.class);
	}

}
