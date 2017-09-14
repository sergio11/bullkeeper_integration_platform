package es.bisite.usal.bullytect.persistence.repository;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bullytect.persistence.entity.PasswordResetTokenEntity;

/**
 * @author sergio
 */
@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetTokenEntity, ObjectId> {
	PasswordResetTokenEntity findByToken(String token);
	PasswordResetTokenEntity findByUser(ObjectId id);
	void deleteByExpiryDateBefore(Date date);
}
