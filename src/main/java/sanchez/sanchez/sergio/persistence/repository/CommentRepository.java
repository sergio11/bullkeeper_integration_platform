package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;


/**
 *
 * @author sergio
 */
@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, ObjectId> {}