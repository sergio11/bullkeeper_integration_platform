
package sanchez.sanchez.sergio.masoc.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.EmailEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.EmailTypeEnum;

/**
 *
 * @author sergio
 */
@Repository
public interface EmailRepository extends MongoRepository<EmailEntity, ObjectId> {
    EmailEntity findByMd5(String md5);
    List<EmailEntity> findAllByOrderByLastChanceAsc(PageRequest pageRequest);
    long deleteBySendToAndType(String sendTo, EmailTypeEnum type);
}
