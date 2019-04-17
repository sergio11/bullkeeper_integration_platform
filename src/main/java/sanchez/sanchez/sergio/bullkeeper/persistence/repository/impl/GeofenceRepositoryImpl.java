package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceAlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GeofenceRepositoryCustom;

/**
 * Geofence Repository
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceRepositoryImpl implements GeofenceRepositoryCustom {

	/**
	 * Mongo Template
	 */
    @Autowired
    private MongoTemplate mongoTemplate;
    
    
	
	/**
	 * Find Alerts
	 */
	@Override
	public Iterable<GeofenceAlertEntity> findAlerts(final ObjectId kid, final ObjectId geofence) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(geofence, "Geofence can not be null");
		
		// Create Query
        final Query query = new Query(Criteria.where("kid").is(kid)
        		.andOperator(Criteria.where("_id").is(geofence)));
        
        query.fields()
        	.include("alerts");    
        
        final GeofenceEntity geofenceEntity = 
        		mongoTemplate.findOne(query, GeofenceEntity.class);
        
        return geofenceEntity != null ? geofenceEntity.getAlerts(): null;
	}

	/**
	 * Delete Alerts
	 */
	@Override
	public void deleteAlerts(final ObjectId kid, final ObjectId geofence) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(geofence, "Geofence can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("kid").is(kid)
		        		.andOperator(Criteria.where("_id").is(geofence))),
                new Update().set("alerts", new ArrayList<>()), GeofenceEntity.class);
	}

	/**
	 * Enable Geofence
	 * @param kid
	 * @param geofence
	 */
	@Override
	public void enableGeofence(final ObjectId kid, final ObjectId geofence) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(geofence, "Geofence can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("kid").is(kid)
		        		.andOperator(Criteria.where("_id").is(geofence))),
                new Update().set("is_enabled", true), GeofenceEntity.class);
		
	}

	/**
	 * Disable Geofence
	 * @param kid
	 * @param geofence
	 */
	@Override
	public void disableGeofence(final ObjectId kid, final ObjectId geofence) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(geofence, "Geofence can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("kid").is(kid)
		        		.andOperator(Criteria.where("_id").is(geofence))),
                new Update().set("is_enabled", false), GeofenceEntity.class);
		
	}

}
