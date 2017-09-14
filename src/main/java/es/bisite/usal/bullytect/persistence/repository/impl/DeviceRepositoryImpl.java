package es.bisite.usal.bullytect.persistence.repository.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import es.bisite.usal.bullytect.persistence.entity.AlertEntity;
import es.bisite.usal.bullytect.persistence.repository.DeviceRepositoryCustom;

/**
 * @author sergio
 */
public class DeviceRepositoryImpl implements DeviceRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(DeviceRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

	@Override
	public void updateDeviceToken(String deviceId, String newToken) {
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("device_id").is(deviceId)),
        		Update.update("registration_token", newToken), AlertEntity.class);
	}
}
