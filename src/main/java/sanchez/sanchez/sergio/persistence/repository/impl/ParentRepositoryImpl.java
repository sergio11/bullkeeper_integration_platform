package sanchez.sanchez.sergio.persistence.repository.impl;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.repository.ParentRepositoryCustom;

/**
 * @author sergio
 */
public class ParentRepositoryImpl implements ParentRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(ParentRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public void setAsNotActiveAndConfirmationToken(ObjectId id, String confirmationToken) {
        Assert.notNull(confirmationToken, "Confirmation Token can not be null");
        Assert.hasLength(confirmationToken, "Confirmation Token can not be empty");
        
        mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(id)),
                new Update().set("is_active", Boolean.FALSE).set("confirmation_token", confirmationToken), ParentEntity.class);
    }

	@Override
	public void setNewPassword(ObjectId id, String newPassword) {
		Assert.notNull(id, "Parent Id can not be null");
		Assert.notNull(newPassword, "New Password can not be null");
        Assert.hasLength(newPassword, "New Password can not be empty");
        
        mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(id)),
                new Update().set("password", newPassword), ParentEntity.class);
	}
}
