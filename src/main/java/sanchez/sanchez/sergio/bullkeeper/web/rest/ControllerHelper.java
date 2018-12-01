
package sanchez.sanchez.sergio.bullkeeper.web.rest;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;

import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author sergio
 */
@Component
public class ControllerHelper {
    
    private final IUploadFilesService uploadFilesService;
    private final  ResourceLoader resourceLoader;

    public ControllerHelper(IUploadFilesService uploadFilesService, ResourceLoader resourceLoader) {
        this.uploadFilesService = uploadFilesService;
        this.resourceLoader = resourceLoader;
    }
    
    private UploadFileInfo getUserDefaultImage() throws IOException{
        final org.springframework.core.io.Resource userDefault = resourceLoader.getResource("classpath:user_default.png");
        return  new UploadFileInfo(userDefault.contentLength(), MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(userDefault.getInputStream()));
    }
    
    public ResponseEntity<byte[]> downloadProfileImage(String profileImageId) throws IOException {
        
        UploadFileInfo imageInfo = null;
    	try {
            
            if(profileImageId != null && !profileImageId.isEmpty()) {
                imageInfo = uploadFilesService.getImage(profileImageId);
                if (imageInfo == null) {
                   imageInfo = getUserDefaultImage();
                }
            } else {
                imageInfo = getUserDefaultImage();
            }
               
        } catch (Exception ex) {
            imageInfo = getUserDefaultImage();
        }
    	
        return ResponseEntity.ok()
                .contentLength(imageInfo.getSize())
                .contentType( imageInfo.getContentType() != null ?  MediaType.parseMediaType(imageInfo.getContentType()) : MediaType.IMAGE_PNG)
                .body(imageInfo.getContent());
    }
    
    
    
}
