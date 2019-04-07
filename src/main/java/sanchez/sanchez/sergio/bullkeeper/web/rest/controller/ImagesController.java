package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

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
import sanchez.sanchez.sergio.bullkeeper.domain.service.IGuardianService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GuardianShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ImageResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Images Controller
 * @author ssanchez
 *
 */
@RestController("ImagesController")
@Validated
@RequestMapping("/api/v1/images/")
@Api(tags = "images", value = "/images/", 
	description = "Management of the images of the users of the platform", produces = "application/json")
public class ImagesController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ImagesController.class);

    private final IGuardianService guardianService;
    private final IKidService kidService;
    private final IUploadFilesService uploadFilesService;
    
    /**
     * 
     * @param guardianService
     * @param kidService
     * @param uploadFilesService
     */
    public ImagesController(final IGuardianService guardianService, final IKidService kidService,
    		final IUploadFilesService uploadFilesService) {
        this.guardianService = guardianService;
        this.kidService = kidService;
        this.uploadFilesService = uploadFilesService;
    }

    /**
     * Upload Guardians Profile Image
     * @param id
     * @param profileImage
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/guardians/{id}/upload", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "UPLOAD_GUARDIANS_PROFILE_IMAGE", nickname = "UPLOAD_GUARDIANS_PROFILE_IMAGE", 
    	notes = "Upload Guardians Profile Image")
    public ResponseEntity<APIResponse<ImageDTO>> uploadGuardianProfileImage(
            @ApiParam(name = "id", value = "Guardian Identifier", required = true) 
    			@Valid @ValidObjectId(message = "{id.not.valid}")
    				@GuardianShouldExists(message = "{guardian.not.exists}")
    				@PathVariable String id,
            @RequestPart("profile_image") MultipartFile profileImage) throws Throwable {
        
        
    	RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType() != null ? profileImage.getContentType() : MediaType.IMAGE_PNG_VALUE, profileImage.getOriginalFilename());
    	ImageDTO imageDTO = guardianService.uploadGuardianProfileImage(new ObjectId(id), uploadProfileImage);
       return ApiHelper.<ImageDTO>createAndSendResponse(ImageResponseCode.IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, imageDTO);

    }
    
    /**
     * Upload Children Profile Image
     * @param id
     * @param profileImage
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/children/{id}/upload", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "UPLOAD_CHILDREN_PROFILE_IMAGE", nickname = "UPLOAD_CHILDREN_PROFILE_IMAGE", 
            notes = "Upload Children Profile Image")
    public ResponseEntity<APIResponse<ImageDTO>> uploadKidProfileImage(
           @ApiParam(name= "id", value = "Kid Identifier", required = true)
    			@Valid @ValidObjectId(message = "{id.not.valid}")
    		 		@PathVariable String id,
            @RequestPart("profile_image") MultipartFile profileImage) throws Throwable {
        
    	RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType() != null ? profileImage.getContentType() : 
                	MediaType.IMAGE_PNG_VALUE, profileImage.getOriginalFilename());
    	ImageDTO imageDTO = kidService.uploadKidProfileImage(new ObjectId(id), uploadProfileImage);
       return ApiHelper.<ImageDTO>createAndSendResponse(ImageResponseCode.IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, imageDTO);

    }

    /**
     * Download Guardian Profile Image
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/guardians/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& (@authorizationService.isYourProfilePublic(#id) || @authorizationService.isYourProfileImage(#id) ))")
    @ApiOperation(value = "DOWNLOAD_GUARDIAN_PROFILE_IMAGE", 
    	nickname = "DOWNLOAD_GUARDIAN_PROFILE_IMAGE", notes = "Download Guardian Profile Image")
    public ResponseEntity<byte[]> downloadGuardianProfileImage(
            @ApiParam(name = "id", value = "Image Identifier", required = true) 
                @Valid @PathVariable String id) throws IOException {
        
        return controllerHelper.downloadImage(id);
    }
    
    /**
     * Download Kid Profile Image
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/children/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.itIsAProfileImageOfSupervisedKid(#id) )")
    @ApiOperation(value = "DOWNLOAD_CHILDREN_PROFILE_IMAGE", nickname = "DOWNLOAD_CHILDREN_PROFILE_IMAGE", notes = "Download Children Profile Image")
    public ResponseEntity<byte[]> downloadKidProfileImage(
            @ApiParam(name = "id", value = "Image Identifier", required = true) 
                @Valid @PathVariable String id) throws IOException {
        
        return controllerHelper.downloadImage(id);
    }
    
 
    
    /**
     * Download Profile Image
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || "
    		+ "( @authorizationService.hasGuardianRole() && @authorizationService.itIsAProfileImageOfSupervisedKid(#id)) || "
    		+ "( @authorizationService.hasGuardianRole() && (@authorizationService.isYourProfilePublic(#id) || @authorizationService.isYourProfileImage(#id) ))")
    @ApiOperation(value = "DOWNLOAD_PROFILE_IMAGE", nickname = "DOWNLOAD_PROFILE_IMAGE", notes = "Download Profile Image")
    public ResponseEntity<byte[]> downloadProfileImage(
            @ApiParam(name = "id", value = "Image Identifier", required = true) 
                @Valid @PathVariable String id) throws IOException {
        
        return controllerHelper.downloadImage(id);
    }
    
    /**
     * Delete Guardian Profile Image
     * @param id
     * @return
     */
    @RequestMapping(value = "/guardians/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourProfileImage(#id) )")
    @ApiOperation(value = "DELETE_GUARDIAN_PROFILE_IMAGE", 
            nickname = "DELETE_GUARDIAN_PROFILE_IMAGE", notes = "Delete Guardian Profile Image")
    public ResponseEntity<APIResponse<String>> deleteGuardianProfileImage(
            @ApiParam(name = "id", value = "Image Identifier", required = true) 
                @Valid @PathVariable String id) {
        uploadFilesService.delete(id);
        return ApiHelper.<String>createAndSendResponse(ImageResponseCode.IMAGE_DELETED_SUCCESSFULLY, 
        		HttpStatus.OK, messageSourceResolver.resolver("image.deleted.successfully"));
    }
    
    /**
     * Delete Kid Profile Image
     * @param id
     * @return
     */
    @RequestMapping(value = "/children/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.itIsAProfileImageOfSupervisedKid(#id) )")
    @ApiOperation(value = "DELETE_KID_PROFILE_IMAGE", 
            nickname = "DELETE_KID_PROFILE_IMAGE", notes = "Delete Kid Profile Image")
    public ResponseEntity<APIResponse<String>> deleteKidProfileImage(
            @ApiParam(name = "id", value = "Image Identifier", required = true) 
                @Valid @PathVariable String id) {
        uploadFilesService.delete(id);
        return ApiHelper.<String>createAndSendResponse(ImageResponseCode.IMAGE_DELETED_SUCCESSFULLY, 
        		HttpStatus.OK, messageSourceResolver.resolver("image.deleted.successfully"));
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(uploadFilesService, "Upload Files Service ...");
    }
}
