package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import java.util.Collection;
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
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.MessageEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.MessageRepositoryCustom;

/**
 * Message Repository Impl
 * @author sergiosanchezsanchez
 *
 */
public final class MessageRepositoryImpl implements MessageRepositoryCustom {
	
	private static Logger logger = LoggerFactory.getLogger(MessageRepositoryImpl.class);
	
	/**
	 * Mongo Template
	 */
	@Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * @param id
	 * @param to
	 */
	@Override
	public String findFirstByConversationIdAndToOrderByCreateAtDesc(final ObjectId id, 
			final ObjectId to) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(to, "To can not be null");
		
		final Criteria criteriaQuery = new Criteria();
		criteriaQuery.andOperator(
				Criteria.where("to.$id").is(to),
				Criteria.where("conversation.$id").is(id));
		
		final Query query = new Query(criteriaQuery);
		query.with(new Sort(Sort.Direction.DESC, "create_at"));
        
        final List<MessageEntity> messageEntityList = 
        		mongoTemplate.find(query, MessageEntity.class);
        
        
        String lastMessageText = null;
        
        if(messageEntityList != null && !messageEntityList.isEmpty()) {
        	final MessageEntity lastMessageEntity = messageEntityList.get(0);
        	lastMessageText = lastMessageEntity != null ? lastMessageEntity.getText(): null;
        	
        }
        
      
		return lastMessageText;
	}

	/**
	 * @param id
	 */
	@Override
	public String findFirstByConversationIdOrderByCreateAtDesc(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		final Criteria criteriaQuery = 
				Criteria.where("conversation.$id").is(id);
		
		final Query query = new Query(criteriaQuery);
		query.with(new Sort(Sort.Direction.DESC, "create_at"));

		final List<MessageEntity> messageEntityList = 
        		mongoTemplate.find(query, MessageEntity.class);
		
		
		String lastMessageText = null;
        
        if(messageEntityList != null && !messageEntityList.isEmpty()) {
        	final MessageEntity lastMessageEntity = messageEntityList.get(0);
        	lastMessageText = lastMessageEntity != null ? lastMessageEntity.getText(): null;
        	
        }
      
		return lastMessageText;
		
	}

	/**
	 * @param conversation
	 * @param to
	 */
	@Override
	public long countByConversationIdAndToAndViewedFalse(final ObjectId conversation, final ObjectId to) {
		Assert.notNull(conversation, "Id can not be null");
		Assert.notNull(to, "To can not be null");
		
		final Criteria criteriaQuery = new Criteria();
		criteriaQuery.andOperator(
				Criteria.where("to.$id").is(to),
				Criteria.where("conversation.$id").is(conversation),
				Criteria.where("viewed").is(false));
		
		return mongoTemplate.count(new Query(criteriaQuery), MessageEntity.class);
	}

	/**
	 * Mark Messages Ad Viewed
	 */
	@Override
	public void markMessagesAsViewed(final Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		
		
		logger.debug("Message Repository update ids size -> " + ids.size());

		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		Update.update("viewed", true), 
        		MessageEntity.class);
		
	}

	/**
	 * @param from
	 * @param to
	 */
	@Override
	public long countByFromIdAndToIdAndViewedFalse(final ObjectId from, final ObjectId to) {
		Assert.notNull(from, "from can not be null");
		Assert.notNull(to, "to can not be null");
		
		final Criteria criteriaQuery = new Criteria();
		criteriaQuery.andOperator(
				Criteria.where("to.$id").is(to),
				Criteria.where("from.$id").is(from),
				Criteria.where("viewed").is(false));
		
		return mongoTemplate.count(new Query(criteriaQuery), MessageEntity.class);
	}

}
