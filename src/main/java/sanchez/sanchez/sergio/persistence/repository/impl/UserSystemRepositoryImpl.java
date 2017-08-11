package sanchez.sanchez.sergio.persistence.repository.impl;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import sanchez.sanchez.sergio.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.persistence.repository.UserSystemRepositoryCustom;

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
