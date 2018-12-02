package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepositoryCustom;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class SupervisedChildrenRepositoryImpl 
		implements SupervisedChildrenRepositoryCustom {
	
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	/**
	 * Accept Supervised Children No Confirm
	 */
	@Override
	public void acceptSupervisedChildrenNoConfirm(ObjectId id) {
		Assert.notNull(id, "id can not be null");
		
		mongoTemplate.updateMulti(
        		new Query(
        				Criteria.where("_id")
        					.is(id)),
        		new Update()
        			.set("is_confirmed", true)
        			.set("request_at", null), 
        		SupervisedChildrenEntity.class);
		
	}

	/**
	 * Accept Supervised Children No Confirmed By ID
	 */
	@Override
	public void acceptSupervisedChildrenNoConfirmById(ObjectId id) {
		Assert.notNull(id, "Guardian can not be null");
		
		mongoTemplate.updateFirst(
        		new Query(
        				Criteria.where("_id")
        					.is(id)),
        		new Update()
        			.set("is_confirmed", true)
        			.set("request_at", null), 
        		SupervisedChildrenEntity.class);
		
	}
	

}
