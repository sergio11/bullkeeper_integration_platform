package es.bisite.usal.bulltect.persistence.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import es.bisite.usal.bulltect.persistence.entity.SchoolEntity;
import es.bisite.usal.bulltect.persistence.repository.SchoolRepositoryCustom;

public class SchoolRepositoryImpl implements SchoolRepositoryCustom {
	
	private static Logger logger = LoggerFactory.getLogger(SchoolRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

	@Override
	public List<SchoolEntity> getAllSchoolNames() {
		
		logger.debug("Get School Names");
		Query query = new Query();
		query.fields().include("_id").include("name");
		
		return mongoTemplate.find(query, SchoolEntity.class);
	}

}
