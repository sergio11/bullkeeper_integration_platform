package sanchez.sanchez.sergio.rest.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.dto.SocialMediaDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.service.ISocialMediaService;

@Api
@RestController("RestSocialMediaController")
@RequestMapping("/api/v1/social/")
public class SocialMediaController implements ISocialMediaHAL {

    private static Logger logger = LoggerFactory.getLogger(SocialMediaController.class);
    
    private final ISocialMediaService socialMediaService;

    public SocialMediaController(ISocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }
    
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "GET_SOCIAL_MEDIA_BY_ID", nickname = "GET_SOCIAL_MEDIA_BY_USER_ID", notes = "Get Social Madia By User Id",
            response = ResponseEntity.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> getSocialMediaById(
            @ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Social Media by Id " + id);
        return Optional.ofNullable(socialMediaService.getSocialMediaById(id))
                .map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
                .map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_USER, socialMediaResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new SocialMediaNotFoundException(); });
    }
}
