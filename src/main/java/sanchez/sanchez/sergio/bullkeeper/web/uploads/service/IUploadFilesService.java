package sanchez.sanchez.sergio.bullkeeper.web.uploads.service;

import org.bson.types.ObjectId;

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
	 * Upload Guardian Profile Image
	 * @param guardian
	 * @param requestUploadFile
	 * @return
	 */
    ImageDTO uploadGuardianProfileImage(final ObjectId guardian,
    		final RequestUploadFile requestUploadFile);
    
    /**
     * Upload Kid Profile Image
     * @param kid
     * @param requestUploadFile
     * @return
     */
    ImageDTO uploadKidProfileImage(final ObjectId kid, 
    		final RequestUploadFile requestUploadFile);
    
    /**
     * 
     * Upload Guardian Profile Image From URL
     * @param guardian
     * @param imageUrl
     * @return
     */
    ImageDTO uploadGuardianProfileImageFromUrl(final ObjectId guardian, final String imageUrl);
    
    /**
     * Get Image
     * @param id
     * @return
     */
    UploadFileInfo getImage(final String id);
    
    /**
     * Delete Image
     * @param id
     */
    void deleteImage(final String id);
    
    
   /**
    * Upload Scheduled Block Image
    * @param kid
    * @param blockId
    * @param requestUploadFile
    * @return
    */
    ImageDTO uploadScheduledBlockImage(final ObjectId kid, final ObjectId blockId, 
    		final RequestUploadFile requestUploadFile);
    
    
    /**
     * Get Scheduled Block Image
     * @param childId
     * @param blockId
     * @return
     */
    UploadFileInfo getScheduledBlockImage(final ObjectId childId, final ObjectId blockId);
    
    
}
