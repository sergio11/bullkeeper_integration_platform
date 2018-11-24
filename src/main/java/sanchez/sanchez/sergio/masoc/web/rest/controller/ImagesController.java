package sanchez.sanchez.sergio.masoc.web.rest.controller;

import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ParentShouldExists;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.masoc.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.masoc.web.rest.ApiHelper;
import sanchez.sanchez.sergio.masoc.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.masoc.web.rest.response.ImageResponseCode;
import sanchez.sanchez.sergio.masoc.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.masoc.web.uploads.service.IUploadFilesService;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;

@RestController("ImagesController")
@Validated
@RequestMapping("/api/v1/images/")
@Api(tags = "images", value = "/images/", description = "Manejo de las imagenes de los usuarios", produces = "application/json")
public class ImagesController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ImagesController.class);
    
    private final IUploadFilesService uploadFilesService;
    
    public ImagesController(IUploadFilesService uploadFilesService) {
        this.uploadFilesService = uploadFilesService;
    }

    @RequestMapping(value = "/parents/{id}/upload", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "UPLOAD_PARENT_PROFILE_IMAGE", nickname = "UPLOAD_PARENT_PROFILE_IMAGE", notes = "Upload Parent Profile Image")
    public ResponseEntity<APIResponse<ImageDTO>> uploadParentProfileImage(
            @ApiParam(name = "id", value = "Identificador del Padre", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}")
    				@ParentShouldExists(message = "{parent.not.exists}")
    				@PathVariable String id,
            @RequestPart("profile_image") MultipartFile profileImage) throws Throwable {
        
        
    	RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType() != null ? profileImage.getContentType() : MediaType.IMAGE_PNG_VALUE, profileImage.getOriginalFilename());
    	ImageDTO imageDTO = uploadFilesService.uploadParentProfileImage(new ObjectId(id), uploadProfileImage);
       return ApiHelper.<ImageDTO>createAndSendResponse(ImageResponseCode.IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, imageDTO);

    }
    
    
    @RequestMapping(value = "/children/{id}/upload", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "UPLOAD_CHILDREN_PROFILE_IMAGE", nickname = "UPLOAD_CHILDREN_PROFILE_IMAGE", 
            notes = "Upload Children Profile Image")
    public ResponseEntity<APIResponse<ImageDTO>> uploadSonProfileImage(
           @ApiParam(name= "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
    		 		@PathVariable String id,
            @RequestPart("profile_image") MultipartFile profileImage) throws Throwable {
        
    	RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType() != null ? profileImage.getContentType() : MediaType.IMAGE_PNG_VALUE, profileImage.getOriginalFilename());
    	ImageDTO imageDTO = uploadFilesService.uploadSonProfileImage(new ObjectId(id), uploadProfileImage);
       return ApiHelper.<ImageDTO>createAndSendResponse(ImageResponseCode.IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, imageDTO);

    }

    @RequestMapping(value = "/parents/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourProfileImage(#id) )")
    @ApiOperation(value = "DOWNLOAD_PARENT_PROFILE_IMAGE", nickname = "DOWNLOAD_PARENT_PROFILE_IMAGE", notes = "Download Parent Profile Image")
    public ResponseEntity<byte[]> downloadParentsProfileImage(
            @ApiParam(name = "id", value = "Identificador de la Imagen", required = true) 
                @Valid @PathVariable String id) throws IOException {
        
        return controllerHelper.downloadProfileImage(id);
    }
    
    @RequestMapping(value = "/children/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.itIsAProfileImageOfYourChild(#id) )")
    @ApiOperation(value = "DOWNLOAD_CHILDREN_PROFILE_IMAGE", nickname = "DOWNLOAD_CHILDREN_PROFILE_IMAGE", notes = "Download Children Profile Image")
    public ResponseEntity<byte[]> downloadChildrenProfileImage(
            @ApiParam(name = "id", value = "Identificador de la Imagen", required = true) 
                @Valid @PathVariable String id) throws IOException {
        
        return controllerHelper.downloadProfileImage(id);
    }
    
    @RequestMapping(value = "/parents/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourProfileImage(#id) )")
    @ApiOperation(value = "DELETE_PARENT_PROFILE_IMAGE", 
            nickname = "DELETE_PARENT_PROFILE_IMAGE", notes = "Delete Parent Profile Image")
    public ResponseEntity<APIResponse<String>> deleteParentProfileImage(
            @ApiParam(name = "id", value = "Identificador de la Imagen", required = true) 
                @Valid @PathVariable String id) {
        uploadFilesService.deleteImage(id);
        return ApiHelper.<String>createAndSendResponse(ImageResponseCode.IMAGE_DELETED_SUCCESSFULLY, 
        		HttpStatus.OK, messageSourceResolver.resolver("image.deleted.successfully"));
    }
    
    
    @RequestMapping(value = "/children/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.itIsAProfileImageOfYourChild(#id) )")
    @ApiOperation(value = "DELETE_PARENT_PROFILE_IMAGE", 
            nickname = "DELETE_PARENT_PROFILE_IMAGE", notes = "Delete Parent Profile Image")
    public ResponseEntity<APIResponse<String>> deleteSonProfileImage(
            @ApiParam(name = "id", value = "Identificador de la Imagen", required = true) 
                @Valid @PathVariable String id) {
        uploadFilesService.deleteImage(id);
        return ApiHelper.<String>createAndSendResponse(ImageResponseCode.IMAGE_DELETED_SUCCESSFULLY, 
        		HttpStatus.OK, messageSourceResolver.resolver("image.deleted.successfully"));
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(uploadFilesService, "Upload Files Service ...");
    }
}
