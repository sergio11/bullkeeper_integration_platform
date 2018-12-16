package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepositoryCustom;

/**
 * Terminal Repository Impl
 * @author sergiosanchezsanchez
 *
 */
public class TerminalRepositoryImpl implements TerminalRepositoryCustom {
	
	@Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * Update Screen Status
	 */
	@Override
	public void updateScreenStatus(final ObjectId terminal, final ObjectId kid, 
			final ScreenStatusEnum screenStatus) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(screenStatus, "Screen Status can not be null");
		
		 mongoTemplate.updateFirst(
	                new Query(Criteria.where("_id").in(terminal)
	                		.andOperator(Criteria.where("kid").in(kid))),
	                new Update()
	                	.set("screen_status", screenStatus.name())
	                	.set("last_time_used", new Date()), TerminalEntity.class);
		
	}

}
