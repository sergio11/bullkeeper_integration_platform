package sanchez.sanchez.sergio.masoc.persistence.repository.impl;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import sanchez.sanchez.sergio.masoc.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.UserSystemRepositoryCustom;

public class UserSystemRepositoryImpl implements UserSystemRepositoryCustom {
	
	@Autowired
    private MongoTemplate mongoTemplate;

	@Override
	public void updateLastLoginAccessAndLastAccessToAlerts(ObjectId id) {
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("id").is(id)),
        		new Update().set("last_login_access", new Date()).set("last_access_to_alerts", new Date()), UserSystemEntity.class);
	}

}
