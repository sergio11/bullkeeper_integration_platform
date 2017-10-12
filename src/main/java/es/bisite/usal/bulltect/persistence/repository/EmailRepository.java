
package es.bisite.usal.bulltect.persistence.repository;

import es.bisite.usal.bulltect.persistence.entity.EmailEntity;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sergio
 */
@Repository
public interface EmailRepository extends MongoRepository<EmailEntity, ObjectId> {
    EmailEntity findByMd5(String md5);
    List<EmailEntity> findAllByOrderByLastChanceAsc(PageRequest pageRequest);
}
