package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DevicePhotoEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.DevicePhotoRepositoryCustom;

/**
 * Device Photo Repository
 * @author ssanchez
 *
 */
public final class DevicePhotoRepositoryImpl implements DevicePhotoRepositoryCustom {
	
private static Logger logger = LoggerFactory.getLogger(DevicePhotoRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;
	
	
    /**
     * Disable Device Photo
     * @param kid
     * @param terminal
     * @param devicePhoto
     */
	@Override
	public void disableDevicePhoto(final ObjectId kid, final ObjectId terminal, final ObjectId devicePhoto) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(devicePhoto, "Device Photo can not be null");
		
		final Criteria criteria = Criteria.where("_id")
				.in(devicePhoto).and("kid").in(kid).and("terminal").in(terminal);
		
		mongoTemplate.updateFirst(
                new Query(criteria),
                new Update()
                	.set("disabled", true), DevicePhotoEntity.class);

	}

}
