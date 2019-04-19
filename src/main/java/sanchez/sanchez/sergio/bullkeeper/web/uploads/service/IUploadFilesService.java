package sanchez.sanchez.sergio.bullkeeper.web.uploads.service;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;

/**
 * Upload Files Service
 * @author sergiosanchezsanchez
 *
 */
public interface IUploadFilesService {
	
    /**
     * Save Image
     * @param requestUploadFile
     * @return
     */
    String save(final RequestUploadFile requestUploadFile);
   
    
    /**
     * Get Image
     * @param id
     * @return
     */
    ImageDTO getImage(final String id);
    
    /**
     * Get File Info
     * @param id
     * @return
     */
    UploadFileInfo getFileInfo(final String id);
    
    /**
     * Delete Image
     * @param id
     */
    void delete(final String id);
    
    /**
     * Delete All
     */
    void deleteAll();
   
    
}
