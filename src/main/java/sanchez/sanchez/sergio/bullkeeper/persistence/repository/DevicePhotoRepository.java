package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DevicePhotoEntity;

/**
 * Device Photo Repository
 * @author ssanchez
 *
 */
@Repository
public interface DevicePhotoRepository extends MongoRepository<DevicePhotoEntity, ObjectId> , 
	DevicePhotoRepositoryCustom {
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param id
	 * @return
	 */
	DevicePhotoEntity findByKidIdAndTerminalIdAndIdAndDisabledFalse(final ObjectId kid, final ObjectId terminal,
			final ObjectId id);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param localId
	 * @return
	 */
	DevicePhotoEntity findByKidIdAndTerminalIdAndLocalId(final ObjectId kid, final ObjectId terminal,
			final String localId);
	
	
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @return
	 */
	Iterable<DevicePhotoEntity> findByKidIdAndTerminalIdAndDisabledFalse(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @return
	 */
	Iterable<DevicePhotoEntity> findByKidIdAndTerminalIdAndDisabledTrue(final ObjectId kid, final ObjectId terminal);

	/**
	 * 
	 * @param kid
	 * @param terminal
	 */
	void deleteByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param ids
	 */
	void deleteByKidIdAndTerminalIdAndIdIn(final ObjectId kid, final ObjectId terminal, final List<ObjectId> ids);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Long countByIdAndDisabledFalse(final ObjectId id);
}
