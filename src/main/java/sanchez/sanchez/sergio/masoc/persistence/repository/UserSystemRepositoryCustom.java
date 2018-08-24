package sanchez.sanchez.sergio.masoc.persistence.repository;



import org.bson.types.ObjectId;
/**
 * @author sergio
 */

public interface UserSystemRepositoryCustom {
	void updateLastLoginAccessAndLastAccessToAlerts(ObjectId id);
}
