package es.bisite.usal.bulltect.web.rest.controller;

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
import es.bisite.usal.bulltect.web.security.userdetails.CommonUserDetailsAware;
import es.bisite.usal.bulltect.web.security.utils.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController("ImagesController")
@Validated
@RequestMapping("/api/v1/images/")
@Api(tags = "images", value = "/images/", description = "Manejo de las imagenes de los usuarios", produces = "application/json")
public class ImagesController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ImagesController.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "UPLOAD_PROFILE_IMAGE_FOR_SELF_USER", nickname = "UPLOAD_PROFILE_IMAGE_FOR_SELF_USER", notes = "Upload Profile Image For Self User")
	public ResponseEntity<APIResponse<String>> uploadProfileImageForSelfUser(
			@RequestPart("profile_image") MultipartFile profileImage,
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
	
	
	}
	
	@GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable Long id){
        UploadFileInfo info = uploadAvatarStrategy.get(id);
        return ResponseEntity.ok()
            .contentLength(info.getSize())
            .contentType(MediaType.parseMediaType(info.getContentType()))
            .body(info.getContent());
    }
	
	@PostConstruct
	protected void init(){}

}
