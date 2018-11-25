package sanchez.sanchez.sergio.bullkeeper.persistence.repository;



import org.bson.types.ObjectId;
/**
 * @author sergio
 */

public interface UserSystemRepositoryCustom {
	void updateLastLoginAccessAndLastAccessToAlerts(ObjectId id);
}
