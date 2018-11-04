package sanchez.sanchez.sergio.masoc.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.AuthorityEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.AuthorityEnum;

/**
 *
 * @author sergio
 */
@Repository
public interface AuthorityRepository extends MongoRepository<AuthorityEntity, ObjectId> {
	
	/*
	 * 
	 */
	AuthorityEntity findByType(AuthorityEnum type);
	
}
