package sanchez.sanchez.sergio.bullkeeper.web.uploads.strategy;

import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;

/**
 * Upload Strategy
 * @author sergiosanchezsanchez
 *
 * @param <T>
 * @param <E>
 */
public interface IUploadStrategy<T, E extends RequestUploadFile> {

	/**
	 * Save
	 * @param fileinfo
	 * @return
	 */
    T save(E fileinfo);

    /**
     * Delete
     * @param id
     */
    void delete(T id);

    /**
     * Get
     * @param id
     * @return
     */
    UploadFileInfo get(T id);
    
    /**
     * Exists
     * @param id
     * @return
     */
    Boolean exists(T id);
    
    /**
     * Delete All
     */
    void deleteAll();
}
