package sanchez.sanchez.sergio.masoc.web.uploads.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.masoc.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.masoc.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.masoc.web.uploads.models.UploadFileInfo;

/**
 * Upload Files Service
 * @author sergiosanchezsanchez
 *
 */
public interface IUploadFilesService {
	
	/**
	 * Upload Parent Profile Image
	 * @param userId
	 * @param requestUploadFile
	 * @return
	 */
    ImageDTO uploadParentProfileImage(final ObjectId userId, final RequestUploadFile requestUploadFile);
    
    /**
     * Upload Son Profile IMage
     * @param userId
     * @param requestUploadFile
     * @return
     */
    ImageDTO uploadSonProfileImage(final ObjectId userId, final RequestUploadFile requestUploadFile);
    
    /**
     * 
     * Upload Parent Profile Image From URL
     * @param userId
     * @param imageUrl
     * @return
     */
    ImageDTO uploadParentProfileImageFromUrl(final ObjectId userId, final String imageUrl);
    
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
    * @param childId
    * @param blockId
    * @param requestUploadFile
    * @return
    */
    ImageDTO uploadScheduledBlockImage(final ObjectId childId, final ObjectId blockId, 
    		final RequestUploadFile requestUploadFile);
    
    
    /**
     * Get Scheduled Block Image
     * @param childId
     * @param blockId
     * @return
     */
    UploadFileInfo getScheduledBlockImage(final ObjectId childId, final ObjectId blockId);
    
    
}
