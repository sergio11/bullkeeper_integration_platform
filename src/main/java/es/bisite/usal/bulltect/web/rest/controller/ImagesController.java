package es.bisite.usal.bulltect.web.rest.controller;

import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.hal.IImageHAL;
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
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.ImageResponseCode;
import es.bisite.usal.bulltect.web.security.userdetails.CommonUserDetailsAware;
import es.bisite.usal.bulltect.web.security.utils.CurrentUser;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;
import es.bisite.usal.bulltect.web.uploads.service.IUploadFilesService;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;

@RestController("ImagesController")
@Validated
@RequestMapping("/api/v1/images/")
@Api(tags = "images", value = "/images/", description = "Manejo de las imagenes de los usuarios", produces = "application/json")
public class ImagesController extends BaseController implements IImageHAL {

    private static Logger logger = LoggerFactory.getLogger(ImagesController.class);
    
    private final IUploadFilesService uploadFilesService;
    
    public ImagesController(IUploadFilesService uploadFilesService) {
        this.uploadFilesService = uploadFilesService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "UPLOAD_PROFILE_IMAGE_FOR_SELF_USER", nickname = "UPLOAD_PROFILE_IMAGE_FOR_SELF_USER", notes = "Upload Profile Image For Self User")
    public ResponseEntity<APIResponse<ImageDTO>> uploadProfileImageForSelfUser(
            @RequestPart("profile_image") MultipartFile profileImage,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
        
        
        RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType(), profileImage.getOriginalFilename());
        ImageDTO imageDto = uploadFilesService.uploadParentProfileImage(selfParent.getUserId(), uploadProfileImage);
        return ApiHelper.<ImageDTO>createAndSendResponse(ImageResponseCode.IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, addLinksToImage(imageDto));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "DOWNLOAD_IMAGE", nickname = "DOWNLOAD_IMAGE", notes = "Download Image by id")
    public ResponseEntity<byte[]> download(
            @ApiParam(name = "id", value = "Identificador de la Imagen", required = true) 
                @Valid @PathVariable String id) {
        
        UploadFileInfo imageInfo = uploadFilesService.getProfileImage(id);
        return ResponseEntity.ok()
                .contentLength(imageInfo.getSize())
                .contentType(MediaType.parseMediaType(imageInfo.getContentType()))
                .body(imageInfo.getContent());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.isYourProfileImage(#id)")
    @ApiOperation(value = "DELETE_IMAGE", nickname = "DELETE_IMAGE", notes = "Delete Image")
    public ResponseEntity<APIResponse<String>> delete(
            @ApiParam(name = "id", value = "Identificador de la Imagen", required = true) 
                @Valid @PathVariable String id) {
        uploadFilesService.deleteProfileImage(id);
        return ApiHelper.<String>createAndSendResponse(ImageResponseCode.IMAGE_DELETED_SUCCESSFULLY, 
        		HttpStatus.OK, messageSourceResolver.resolver("image.deleted.successfully"));
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(uploadFilesService, "Upload Files Service ...");
    }
}
