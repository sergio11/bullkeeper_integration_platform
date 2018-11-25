package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;

/**
 * Supervised Children Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface SupervisedChildrenRepository extends 
			MongoRepository<SupervisedChildrenEntity, ObjectId> {
	
	/**
	 * Find By Guardian Id And Is Confirmed
	 * @param id
	 * @return
	 */
	List<SupervisedChildrenEntity> findByGuardianIdAndIsConfirmed(final ObjectId id, final boolean isConfirmed);
	
	/**
	 * Find BY Guardian Id
	 * @param id
	 * @return
	 */
	List<SupervisedChildrenEntity> findByGuardianId(final ObjectId id);
	
	
	/**
	 * Find By Guardian Id And Role And Is Confirmed
	 * @param id
	 * @param role
	 * @param isConfirmed
	 * @return
	 */
	List<SupervisedChildrenEntity> findByGuardianIdAndRoleAndIsConfirmed(final ObjectId id, final GuardianRolesEnum role, 
			final boolean isConfirmed);

	/**
	 * Count By Kid Id And Role And Is Confirmed
	 * @param id
	 * @param role
	 * @param isConfirmed
	 * @return
	 */
	long countByKidIdAndRoleAndIsConfirmed(final ObjectId id, 
			final GuardianRolesEnum role, final boolean isConfirmed);
	
	/**
	 * Find By Kid Id And Is Confirmed
	 * @param id
	 * @param isConfirmed
	 * @return
	 */
	List<SupervisedChildrenEntity> findByKidIdAndIsConfirmed(final ObjectId id,
			final boolean isConfirmed);
	
	
	/**
	 * Count By Guardian Id And Is Confirmed
	 * @param id
	 * @param isConfirmed
	 * @return
	 */
	long countByGuardianIdAndIsConfirmed(final ObjectId id, final boolean isConfirmed);
	
	/**
	 * Count By Guardian Id And Kid Profile Image
	 * @param id
	 * @param profileImage
	 * @return
	 */
	long countByGuardianIdAndKidProfileImage(final ObjectId id, final String profileImage);
	
}
