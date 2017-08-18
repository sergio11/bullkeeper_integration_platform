package sanchez.sanchez.sergio.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;

/**
 * @author sergio
 */
public interface AlertRepositoryCustom {
	void setAsDelivered(List<ObjectId> alertIds);
	void setAsDelivered(ObjectId alertId);
}
