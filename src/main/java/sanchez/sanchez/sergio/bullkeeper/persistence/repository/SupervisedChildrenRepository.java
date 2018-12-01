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
			MongoRepository<SupervisedChildrenEntity, ObjectId> , SupervisedChildrenRepositoryCustom {
	
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
	 * Find by kid Id
	 * @param id
	 * @return
	 */
	List<SupervisedChildrenEntity> findByKidId(final ObjectId id);
	
	
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
	 * Find By Guardian Id And Kid Id And Is Confirmed True
	 * @param guardian
	 * @param kid
	 * @return
	 */
	List<SupervisedChildrenEntity> findByGuardianIdAndKidIdAndIsConfirmedTrue(final ObjectId guardian, final ObjectId kid);
	
	/**
	 * Find By Guardian Id And Kid Id And Is Confirmed False
	 * @param guardian
	 * @param kid
	 * @return
	 */
	List<SupervisedChildrenEntity> findByGuardianIdAndKidIdAndIsConfirmedFalse(final ObjectId guardian, final ObjectId kid);
	
	/**
	 * Delete By Guardian Id And Kid Id And Is Confirmed False
	 * @param guardian
	 * @param kid
	 * @return
	 */
	void deleteByGuardianIdAndKidIdAndIsConfirmedFalse(final ObjectId guardian, final ObjectId kid);
	
	/**
	 * Count By Guardian Id And Is Confirmed
	 * @param id
	 * @param isConfirmed
	 * @return
	 */
	long countByGuardianIdAndIsConfirmed(final ObjectId id, final boolean isConfirmed);
	
	/**
	 * Count By Guardian Id And Kid Id And is confirmed true
	 * @param guardian
	 * @param kid
	 * @return
	 */
	long countByGuardianIdAndKidIdAndIsConfirmedTrue(final ObjectId guardian, final ObjectId kid);
	
	/**
	 * Count By Guardian Id And Kid Profile Image
	 * @param id
	 * @param profileImage
	 * @return
	 */
	long countByGuardianIdAndKidProfileImage(final ObjectId id, final String profileImage);
	
	/**
	 * 
	 * @param guardian
	 * @param kid
	 * @param roles
	 * @return
	 */
	long countByGuardianIdAndKidIdAndRoleInAndIsConfirmedTrue(final ObjectId guardian, final ObjectId kid, final List<GuardianRolesEnum> roles);
	
	/**
	 * Delete By Kid Id
	 * @param id
	 */
	void deleteByKidId(final ObjectId id);
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * Find By Guardian Id And Is Confirmed True
	 * @param guardian
	 * @return
	 */
	List<SupervisedChildrenEntity> findByGuardianIdAndIsConfirmedTrue(final ObjectId guardian);
	
	/**
	 * Find By Guardian Id And Is Confirmed False
	 * @param guardian
	 * @return
	 */
	List<SupervisedChildrenEntity> findByGuardianIdAndIsConfirmedFalse(final ObjectId guardian);
	
	
	/**
	 * Delete By Guardian Id And Is Confirmed False
	 * @param guardian
	 * @param kid
	 * @return
	 */
	void deleteByGuardianIdAndIsConfirmedFalse(final ObjectId guardian);
	
	/**
	 * Delete By Id and is Confirmed False
	 * @param guardian
	 */
	void deleteByIdAndIsConfirmedFalse(final ObjectId guardian);
	
	/**
	 * Delete By Id and is confirmed true
	 * @param guardian
	 */
	void deleteByIdAndIsConfirmedTrue(final ObjectId guardian);
	
	/**
	 * Find By Id And Is Confirmed True
	 * @param id
	 * @return
	 */
	SupervisedChildrenEntity findByIdAndIsConfirmedTrue(final ObjectId id);
	
	/**
	 * Find By Id And Is Confirmed False
	 * @param id
	 * @return
	 */
	SupervisedChildrenEntity findByIdAndIsConfirmedFalse(final ObjectId id);
	
	
	/**
	 * Count By Guardian Id
	 * @param id
	 * @param isConfirmed
	 * @return
	 */
	long countByGuardianId(final ObjectId guardian);
	
	/**
	 * Count By Guardian Id And Is Confirmed True
	 * @param id
	 * @param isConfirmed
	 * @return
	 */
	long countByGuardianIdAndIsConfirmedTrue(final ObjectId guardian);
	
	/**
	 * Count By Guardian Id And Is Confirmed False
	 * @param id
	 * @param isConfirmed
	 * @return
	 */
	long countByGuardianIdAndIsConfirmedFalse(final ObjectId guardian);

}
