package sanchez.sanchez.sergio.persistence.repository;

import java.util.Date;

import org.bson.types.ObjectId;
/**
 * @author sergio
 */

public interface UserSystemRepositoryCustom {
	void updateLastLoginAccess(ObjectId id, Date lastLoginAccess);
}
