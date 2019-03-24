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

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ContactEntityRepositoryCustom;

/**
 *
 * @author sergio
 */
public class ContactEntityRepositoryImpl implements ContactEntityRepositoryCustom {
	
	private static Logger logger = LoggerFactory.getLogger(ContactEntityRepositoryImpl.class);
    
	/**
	 * Mongo Template
	 */
    @Autowired
    private MongoTemplate mongoTemplate;

    
    /**
     * Disable Contact
     * @param kid
     * @param terminal
     * @param contact
     */
	@Override
	public void disableContact(final ObjectId kid, final ObjectId terminal, final ObjectId contact) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(contact, "Contact can not be null");
		
		final Criteria criteria = Criteria.where("_id")
				.in(contact).and("kid").in(kid).and("terminal").in(terminal);
		
		mongoTemplate.updateFirst(
                new Query(criteria),
                new Update()
                	.set("disabled", true), ContactEntity.class);
		
	}

  
}
