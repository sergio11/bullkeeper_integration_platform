package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ConversationRepositoryCustom;

/**
 * Conversation Repository
 * @author sergiosanchezsanchez
 *
 */
public final class ConversationRepositoryImpl implements ConversationRepositoryCustom {
	
	private Logger logger = LoggerFactory.getLogger(ConversationRepositoryImpl.class);
	
	/**
	 * Mongo Template
	 */
	@Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * @param id
	 */
	@Override
	public Iterable<ConversationEntity> findByMemberId(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		logger.debug("Find By Member with id -> " + id.toString());
		
		final Criteria criteriaQuery = new Criteria();
		criteriaQuery.orOperator(
				Criteria.where("member_one.$id").is(id),
				Criteria.where("member_two.$id").is(id));
        
        // Find conversations
        final Iterable<ConversationEntity> conversationEntityList = 
        		mongoTemplate.find(new Query(criteriaQuery), ConversationEntity.class);
		
		return conversationEntityList;
	}

	/**
	 * Count By Member Id
	 * @param id
	 */
	@Override
	public long countByMemberId(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		final Criteria criteriaQuery = new Criteria();
		criteriaQuery.orOperator(
				Criteria.where("member_one.$id").is(id),
				Criteria.where("member_two.$id").is(id));
		
        return mongoTemplate.count(new Query(criteriaQuery), ConversationEntity.class);
	}

	/**
	 * @param memberOne
	 * @param memberTwo
	 */
	@Override
	public ConversationEntity findByMemberOneIdAndMemberTwoId(
			final ObjectId memberOne, final ObjectId memberTwo) {
		Assert.notNull(memberOne, "Member One can not be null");
		Assert.notNull(memberTwo, "Member Two can not be null");
				
		
		final Criteria firstCriteria = new Criteria();
		firstCriteria.andOperator(
				Criteria.where("member_one.$id").is(memberOne),
				Criteria.where("member_two.$id").is(memberTwo));
		
		final Criteria secondCriteria = new Criteria();
		secondCriteria.andOperator(
				Criteria.where("member_one.$id").is(memberTwo),
				Criteria.where("member_two.$id").is(memberOne));
		
		final Criteria criteriaQuery = new Criteria();
		criteriaQuery.orOperator(firstCriteria, secondCriteria);
	
	
        // Conversation Entity
        final ConversationEntity conversationEntity = 
        		mongoTemplate.findOne(new Query(criteriaQuery), ConversationEntity.class);
		
		return conversationEntity;
	}

	/**
	 * Delete By Member Id
	 */
	@Override
	public void deleteByMemberId(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		final Criteria criteriaQuery = new Criteria();
		criteriaQuery.orOperator(
				Criteria.where("member_one.$id").is(id),
				Criteria.where("member_two.$id").is(id));
		
		mongoTemplate.remove(new Query(criteriaQuery), ConversationEntity.class);
	}

}
