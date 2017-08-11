package sanchez.sanchez.sergio.rest.controller;


import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import sanchez.sanchez.sergio.dto.request.AddSocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.CommentDTO;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.rest.exception.CommentsBySonNotFoundException;
import sanchez.sanchez.sergio.rest.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.rest.exception.SonNotFoundException;
import sanchez.sanchez.sergio.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.rest.hal.ISonHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.rest.response.ChildrenResponseCode;
import sanchez.sanchez.sergio.service.ICommentsService;
import sanchez.sanchez.sergio.service.ISocialMediaService;
import sanchez.sanchez.sergio.service.ISonService;

@Api
@RestController("RestUserController")
@Validated
@RequestMapping("/api/v1/children/")
public class ChildrenController implements ISonHAL, ICommentHAL, ISocialMediaHAL {

    private static Logger logger = LoggerFactory.getLogger(ChildrenController.class);
    
    private final ISonService sonService;
    private final ICommentsService commentService;
    private final ISocialMediaService socialMediaService;
    
    public ChildrenController(ISonService sonService, ICommentsService commentService, ISocialMediaService socialMediaService) {
        this.sonService = sonService;
        this.commentService = commentService;
        this.socialMediaService = socialMediaService;
    }
    
    @GetMapping(path = {"/", "/all"})
    @ApiOperation(value = "GET_ALL_CHILDREN", nickname = "GET_ALL_CHILDREN", 
            notes = "Get all Children", response = ResponseEntity.class)
    @PreAuthorize("@authorizationService.hasAdminRole()")
    public ResponseEntity<APIResponse<PagedResources<Resource<SonDTO>>>> getAllChildren(@PageableDefault Pageable pageable, 
            PagedResourcesAssembler<SonDTO> pagedAssembler) throws Throwable {
        logger.debug("Get all Children");
        return Optional.ofNullable(sonService.findPaginated(pageable))
                .map(childrenPage -> addLinksToChildren(childrenPage))
                .map(childrenPage -> pagedAssembler.toResource(childrenPage))
                .map(childrenPageResource -> ApiHelper.<PagedResources<Resource<SonDTO>>>createAndSendResponse(ChildrenResponseCode.ALL_USERS, HttpStatus.OK, childrenPageResource))
                .orElseThrow(() -> { throw new ResourceNotFoundException(); });
    }
    

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "GET_SON_BY_ID", nickname = "GET_SON_BY_ID", notes = "Get Son By Id",
            response = ResponseEntity.class)
    @PreAuthorize("@authorizationService.hasParentRole() && @authorizationService.isYourSon(#id)")
    public ResponseEntity<APIResponse<SonDTO>> getSonById(
    		@Valid @ValidObjectId(message = "{son.id.notvalid}")
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get User with id: " + id);
        return Optional.ofNullable(sonService.getSonById(id))
                .map(sonResource -> addLinksToSon(sonResource))
                .map(sonResource -> ApiHelper.<SonDTO>createAndSendResponse(ChildrenResponseCode.SINGLE_USER, HttpStatus.OK, sonResource))
                .orElseThrow(() -> { throw new SonNotFoundException(); });
    }
    
    
    @GetMapping(path = "/{id}/comments")
    @ApiOperation(value = "GET_COMMENTS_BY_SON", nickname = "GET_COMMENTS_BY_SON", notes = "Get Comments By Son Id",
            response = ResponseEntity.class)
    @PreAuthorize("@authorizationService.hasParentRole() && @authorizationService.isYourSon(#id)")
    public ResponseEntity<APIResponse<PagedResources<Resource<CommentDTO>>>> getCommentsBySonId(
            @PageableDefault Pageable pageable, 
            PagedResourcesAssembler<CommentDTO> pagedAssembler,
            @Valid @ValidObjectId(message = "{son.id.notvalid}")
            @ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Comments by user with id: " + id);
        return Optional.ofNullable(commentService.getCommentBySonId(pageable, id))
                .map(commentsPage -> addLinksToComments(commentsPage))
                .map(commentsPage -> pagedAssembler.toResource(commentsPage))
                .map(commentsPageResource -> ApiHelper.<PagedResources<Resource<CommentDTO>>>createAndSendResponse(CommentResponseCode.ALL_COMMENTS_BY_CHILD, 
                		HttpStatus.OK, commentsPageResource))
                .orElseThrow(() -> { throw new CommentsBySonNotFoundException(); });
    }
    
    @GetMapping(path = "/{id}/social")
    @ApiOperation(value = "GET_SOCIAL_MEDIA_BY_SON", nickname = "GET_SOCIAL_MEDIA_BY_SON", notes = "Get Social Madia By Son",
            response = ResponseEntity.class)
    @PreAuthorize("@authorizationService.hasParentRole() && @authorizationService.isYourSon(#id)")
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getSocialMediaBySonId(
    		@Valid @ValidObjectId(message = "{son.id.notvalid}")
            @ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Social Media by User Id " + id);
        return Optional.ofNullable(socialMediaService.getSocialMediaByUser(id))
                .map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
                .map(socialMediaResource -> ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_CHILD, 
                		HttpStatus.OK, socialMediaResource))
                .orElseThrow(() -> { throw new SocialMediaNotFoundException(); });
    }
    
    @PutMapping(path = "/{id}/social/add")
    @ApiOperation(value = "ADD_SOCIAL_MEDIA_TO_SON", nickname = "ADD_SOCIAL_MEDIA_TO_SON", notes = "Add Social Media To Son",
            response = ResponseEntity.class)
    @PreAuthorize("@authorizationService.hasParentRole() && @authorizationService.isYourSon(#id)")
    public ResponseEntity<APIResponse<SocialMediaDTO>> addSocialMediaToSon(
    		@Valid @ValidObjectId(message = "{son.id.notvalid}")
            @ApiParam(value = "id", required = true) @PathVariable String id,
            @ApiParam(value = "socialMedia", required = true) 
				@Valid @RequestBody AddSocialMediaDTO addSocialMediaDTO) throws Throwable {
        logger.debug("Add Social Media To Son with id -> " + id);
        return Optional.ofNullable(socialMediaService.save(addSocialMediaDTO))
        		.map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
        		.map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_ADDED, 
        				HttpStatus.OK, socialMediaResource))
        		.orElseThrow(() -> { throw new SocialMediaNotFoundException(); });        
    }
    
    @GetMapping(path = "/{id}/social/invalid")
    @ApiOperation(value = "GET_INVALID_SOCIAL_MEDIA_BY_SON", nickname = "GET_INVALID_SOCIAL_MEDIA_BY_SON", notes = "Get Social Madia By Son with invalid token",
            response = ResponseEntity.class)
    @PreAuthorize("@authorizationService.hasParentRole() && @authorizationService.isYourSon(#id)")
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getInvalidSocialMediaBySonId(
    		@Valid @ValidObjectId(message = "{son.id.notvalid}")
            @ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Invalid Social  Media by User Id " + id);
        return Optional.ofNullable(socialMediaService.getInvalidSocialMediaById(id))
                .map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
                .map(socialMediaResource -> ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.INVALID_SOCIAL_MEDIA_BY_CHILD, 
                		HttpStatus.OK, socialMediaResource))
                .orElseThrow(() -> { throw new SocialMediaNotFoundException(); });
    }
}
