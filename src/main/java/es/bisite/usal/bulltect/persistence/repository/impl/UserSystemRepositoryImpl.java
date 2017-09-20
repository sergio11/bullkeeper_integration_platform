package es.bisite.usal.bulltect.persistence.repository.impl;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import es.bisite.usal.bulltect.persistence.entity.UserSystemEntity;
import es.bisite.usal.bulltect.persistence.repository.UserSystemRepositoryCustom;

public class UserSystemRepositoryImpl implements UserSystemRepositoryCustom {
	
	@Autowired
    private MongoTemplate mongoTemplate;

	@Override
	public void updateLastLoginAccess(ObjectId id, Date lastLoginAccess) {
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("id").is(id)),
        		Update.update("last_login_access", lastLoginAccess), UserSystemEntity.class);
	}

}
