package sanchez.sanchez.sergio.rest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.rest.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.service.ISocialMediaService;

@Api
@RestController("RestSocialMediaController")
@Validated
@RequestMapping("/api/v1/social/")
public class SocialMediaController implements ISocialMediaHAL {

    private static Logger logger = LoggerFactory.getLogger(SocialMediaController.class);
    
    private final ISocialMediaService socialMediaService;

    public SocialMediaController(ISocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }
    
    
    @GetMapping(path = {"/", "/all"})
    @ApiOperation(value = "GET_ALL_SOCIAL_MEDIA", nickname = "GET_ALL_SOCIAL_MEDIA", 
            notes = "Get all Social Media", response = PagedResources.class)
    @PreAuthorize("@authorizationService.hasAdminRole()")
    public ResponseEntity<APIResponse<PagedResources<Resource<SocialMediaDTO>>>> getAllSocialMedia(@PageableDefault Pageable pageable, 
            PagedResourcesAssembler<SocialMediaDTO> pagedAssembler) throws Throwable {
        logger.debug("Get all Social Media");
        return Optional.ofNullable(socialMediaService.findPaginated(pageable))
                .map(socialMediaPage -> addLinksToSocialMedia(socialMediaPage))
                .map(socialMediaPage -> pagedAssembler.toResource(socialMediaPage))
                .map(socialMediaPageResource -> ApiHelper.<PagedResources<Resource<SocialMediaDTO>>>createAndSendResponse(SocialMediaResponseCode.ALL_SOCIAL_MEDIA, 
                		HttpStatus.OK, socialMediaPageResource))
                .orElseThrow(() -> { throw new ResourceNotFoundException(); });
    }
    
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "GET_SOCIAL_MEDIA_BY_ID", nickname = "GET_SOCIAL_MEDIA_BY_USER_ID", notes = "Get Social Madia By User Id",
            response = SocialMediaDTO.class)
    @PreAuthorize("@authorizationService.hasAdminRole()")
    public ResponseEntity<APIResponse<SocialMediaDTO>> getSocialMediaById(
    		@Valid @ValidObjectId(message = "{socialmedia.id.notvalid}")
            @ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Social Media by Id " + id);
        return Optional.ofNullable(socialMediaService.getSocialMediaById(id))
                .map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
                .map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_CHILD, 
                		HttpStatus.OK, socialMediaResource))
                .orElseThrow(() -> { throw new SocialMediaNotFoundException(); });
    }
}
