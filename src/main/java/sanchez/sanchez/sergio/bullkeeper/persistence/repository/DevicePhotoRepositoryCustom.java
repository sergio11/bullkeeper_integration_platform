package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;

/**
 * Device Photo Repository
 * @author ssanchez
 *
*/
public interface DevicePhotoRepositoryCustom {
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param devicePhoto
	 */
	void disableDevicePhoto(final ObjectId kid, final ObjectId terminal, final ObjectId devicePhoto);

}
