package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PasswordResetTokenEntity;

/**
 * @author sergio
 */
@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetTokenEntity, ObjectId> {
	PasswordResetTokenEntity findByToken(String token);
	PasswordResetTokenEntity findByUser(ObjectId id);
	void deleteByExpiryDateBefore(Date date);
}
