package sanchez.sanchez.sergio.rest.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import sanchez.sanchez.sergio.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.CommentDTO;
import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.rest.exception.CommentsBySonNotFoundException;
import sanchez.sanchez.sergio.rest.exception.NoChildrenFoundException;
import sanchez.sanchez.sergio.rest.exception.SonNotFoundException;
import sanchez.sanchez.sergio.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.rest.hal.ISonHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.security.utils.OnlyAccessForAdmin;
import sanchez.sanchez.sergio.rest.response.ChildrenResponseCode;
import sanchez.sanchez.sergio.service.ICommentsService;
import sanchez.sanchez.sergio.service.ISocialMediaService;
import sanchez.sanchez.sergio.service.ISonService;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.data.domain.Page;
import sanchez.sanchez.sergio.persistence.constraints.SocialMediaShouldExists;


@RestController("RestUserController")
@Validated
@RequestMapping("/api/v1/children/")
@Api(tags = "children", value = "/children/", description = "Punto de Entrada para el manejo de usuarios analizados", produces = "application/json")
public class ChildrenController extends BaseController implements ISonHAL, ICommentHAL, ISocialMediaHAL {

    private static Logger logger = LoggerFactory.getLogger(ChildrenController.class);
    
    private final ISonService sonService;
    private final ICommentsService commentService;
    private final ISocialMediaService socialMediaService;
    
    public ChildrenController(ISonService sonService, ICommentsService commentService, ISocialMediaService socialMediaService) {
        this.sonService = sonService;
        this.commentService = commentService;
        this.socialMediaService = socialMediaService;
    }
    
    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_CHILDREN", nickname = "GET_ALL_CHILDREN", notes = "Get all Children", response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<SonDTO>>>> getAllChildren(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<SonDTO> pagedAssembler) throws Throwable {
        
    	logger.debug("Get all Children");
    	Page<SonDTO> childrenPage = sonService.findPaginated(pageable);
        if(childrenPage.getTotalElements() == 0)
        	throw new NoChildrenFoundException();
        return ApiHelper.<PagedResources<Resource<SonDTO>>>createAndSendResponse(ChildrenResponseCode.ALL_USERS, 
            		HttpStatus.OK, pagedAssembler.toResource(addLinksToChildren((childrenPage))));
    }
    

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_SON_BY_ID", nickname = "GET_SON_BY_ID", notes = "Get Son By Id", response = SonDTO.class)
    public ResponseEntity<APIResponse<SonDTO>> getSonById(
    		@ApiParam(name= "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
    		 		@PathVariable String id) throws Throwable {
        logger.debug("Get User with id: " + id);
        
        return Optional.ofNullable(sonService.getSonById(id))
                .map(sonResource -> addLinksToSon(sonResource))
                .map(sonResource -> ApiHelper.<SonDTO>createAndSendResponse(ChildrenResponseCode.SINGLE_USER, HttpStatus.OK, sonResource))
                .orElseThrow(() -> { throw new SonNotFoundException(); });
    }
    
  
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_COMMENTS_BY_SON", nickname = "GET_COMMENTS_BY_SON", notes = "Get Comments By Son Id",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<CommentDTO>>>> getCommentsBySonId(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<CommentDTO> pagedAssembler,
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        logger.debug("Get Comments by user with id: " + id);
        
        Page<CommentDTO> commentsPage = commentService.getCommentBySonId(pageable, id);
        
        if(commentsPage.getTotalElements() == 0)
        	throw new CommentsBySonNotFoundException();
        
        return ApiHelper.<PagedResources<Resource<CommentDTO>>>createAndSendResponse(CommentResponseCode.ALL_COMMENTS_BY_CHILD, 
        		HttpStatus.OK, pagedAssembler.toResource(addLinksToComments((commentsPage))));
        
    }
    
    @RequestMapping(value = "/{id}/social", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_SOCIAL_MEDIA_BY_SON", nickname = "GET_SOCIAL_MEDIA_BY_SON", notes = "Get Social Madia By Son",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getSocialMediaBySonId(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
    				@PathVariable String id) throws Throwable {
        logger.debug("Get Social Media by User Id " + id);
        
        Iterable<SocialMediaDTO> socialMedia = socialMediaService.getSocialMediaByUser(id);
        
        if(Iterables.size(socialMedia) == 0)
        	throw new SocialMediaNotFoundException();
        
        return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_CHILD, 
        		HttpStatus.OK, addLinksToSocialMedia(socialMedia));
    }
    
    @RequestMapping(value = "/social/add", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#socialMedia.son) )")
    @ApiOperation(value = "ADD_SOCIAL_MEDIA", nickname = "ADD_SOCIAL_MEDIA", notes = "Add Social Media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> deleteAllSocialMedia(
            @ApiParam(value = "socialMedia", required = true) 
				@Validated(ICommonSequence.class) @RequestBody SaveSocialMediaDTO socialMedia) throws Throwable {
    		
        return Optional.ofNullable(socialMediaService.create(socialMedia))
        		.map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
        		.map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_ADDED, 
        				HttpStatus.OK, socialMediaResource))
        		.orElseThrow(() -> { throw new SocialMediaNotFoundException(); });        
    }
    
    
    @RequestMapping(value = "/social/update", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "UPDATE_SOCIAL_MEDIA", nickname = "UPDATE_SOCIAL_MEDIA", notes = "Update Social Media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> updateSocialMediaToSon(
            @ApiParam(value = "socialMedia", required = true) 
				@Validated(ICommonSequence.class) @RequestBody SaveSocialMediaDTO socialMedia) throws Throwable {
    		
        return Optional.ofNullable(socialMediaService.update(socialMedia))
        		.map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
        		.map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_UPDATED, 
        				HttpStatus.OK, socialMediaResource))
        		.orElseThrow(() -> { throw new SocialMediaNotFoundException(); });        
    }
    
    @RequestMapping(value = "/social/save", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#socialMedia.son) )")
    @ApiOperation(value = "SAVE_SOCIAL_MEDIA", nickname = "SAVE_SOCIAL_MEDIA", notes = "Save Social Media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> saveSocialMediaToSon(
            @ApiParam(value = "socialMedia", required = true) 
				@Validated(ICommonSequence.class) @RequestBody SaveSocialMediaDTO socialMedia) throws Throwable {
    		
        return Optional.ofNullable(socialMediaService.save(socialMedia))
        		.map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
        		.map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_SAVED, 
        				HttpStatus.OK, socialMediaResource))
        		.orElseThrow(() -> { throw new SocialMediaNotFoundException(); });        
    }
    
    @RequestMapping(value = "/{id}/social/delete/all", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "DELETE_ALL_SOCIAL_MEDIA", nickname = "DELETE_ALL_SOCIAL_MEDIA", notes = "Delete all social media of user",
            response = List.class)
    //@OnlyAccessForAdminOrParentOfTheSon(son = "#id")
    public ResponseEntity<APIResponse<List<SocialMediaDTO>>> deleteAllSocialMedia(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
        List<SocialMediaDTO> socialMediaEntities = socialMediaService.deleteSocialMediaByUser(id);
        
        return ApiHelper.<List<SocialMediaDTO>>createAndSendResponse(
                SocialMediaResponseCode.ALL_USER_SOCIAL_MEDIA_DELETED, HttpStatus.OK, socialMediaEntities);
    }
    
    @RequestMapping(value = "/{idson}/social/delete/{idsocial}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "DELETE_SOCIAL_MEDIA", nickname = "DELETE_SOCIAL_MEDIA", notes = "Delete a single social media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> deleteSocialMedia(
    		@ApiParam(name = "idson", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String idson,
            @ApiParam(name = "idsocial", value = "Identificador del Medio Social", required = true)
            	@Validated(ICommonSequence.class) @ValidObjectId(message = "{social.id.notvalid}")
            		@SocialMediaShouldExists(message = "{social.not.exists}")
             			@PathVariable String idsocial) throws Throwable {
        
        SocialMediaDTO socialMediaEntities = socialMediaService.deleteSocialMediaById(idsocial);
        return ApiHelper.<SocialMediaDTO>createAndSendResponse(
                SocialMediaResponseCode.USER_SOCIAL_MEDIA_DELETED, HttpStatus.OK, socialMediaEntities);
    }
    
   
    @RequestMapping(value = "/{id}/social/invalid", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_INVALID_SOCIAL_MEDIA_BY_SON", nickname = "GET_INVALID_SOCIAL_MEDIA_BY_SON", notes = "Get Social Madia By Son with invalid token",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getInvalidSocialMediaBySonId(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        logger.debug("Get Invalid Social  Media by User Id " + id);
        
        Iterable<SocialMediaDTO> socialMedia = socialMediaService.getInvalidSocialMediaById(id);
        if(Iterables.size(socialMedia) == 0)
        	throw new SocialMediaNotFoundException();
        return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.INVALID_SOCIAL_MEDIA_BY_CHILD, 
        		HttpStatus.OK, addLinksToSocialMedia(socialMedia));
    }
}
