package es.bisite.usal.bulltect.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;


/**
 *
 * @author sergio
 */
@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, ObjectId>, CommentRepositoryCustom {
    Page<CommentEntity> findAllBySonEntityId(ObjectId userId, Pageable pageable);
}
