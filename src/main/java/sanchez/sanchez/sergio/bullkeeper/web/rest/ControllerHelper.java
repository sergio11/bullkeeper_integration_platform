
package sanchez.sanchez.sergio.bullkeeper.web.rest;


import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
    
    private UploadFileInfo getDefaultImage() throws IOException{
        final org.springframework.core.io.Resource userDefault = resourceLoader.getResource("classpath:user_default.png");
        return  new UploadFileInfo(userDefault.contentLength(), MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(userDefault.getInputStream()));
    }
    
    
    public ResponseEntity<byte[]> downloadImage(String imageId) throws IOException {
        
        UploadFileInfo imageInfo = null;
    	try {
            
            if(imageId != null && !imageId.isEmpty()) {
                imageInfo = uploadFilesService.getFileInfo(imageId);
                if (imageInfo == null) {
                   imageInfo = getDefaultImage();
                }
            } else {
                imageInfo = getDefaultImage();
            }
               
        } catch (Exception ex) {
            imageInfo = getDefaultImage();
        }
    	
        return ResponseEntity.ok()
                .contentLength(imageInfo.getSize())
                .contentType( imageInfo.getContentType() != null ?  MediaType.parseMediaType(imageInfo.getContentType()) : MediaType.IMAGE_PNG)
                .body(imageInfo.getContent());
    }
    
    
    
}
