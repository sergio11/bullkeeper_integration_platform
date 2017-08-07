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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;

import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.UserDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.rest.exception.CommentsByUserNotFoundException;
import sanchez.sanchez.sergio.rest.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.rest.exception.UserNotFoundException;
import sanchez.sanchez.sergio.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.rest.hal.IUserHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.rest.response.UserResponseCode;
import sanchez.sanchez.sergio.service.ICommentsService;
import sanchez.sanchez.sergio.service.ISocialMediaService;
import sanchez.sanchez.sergio.service.IUserService;

@Api
@RestController("RestUserController")
@RequestMapping("/api/v1/users/")
public class UsersController implements IUserHAL, ICommentHAL, ISocialMediaHAL {

    private static Logger logger = LoggerFactory.getLogger(UsersController.class);
    
    private final IUserService userService;
    private final ICommentsService commentService;
    private final ISocialMediaService socialMediaService;

    public UsersController(IUserService userService, ICommentsService commentService, ISocialMediaService socialMediaService) {
        this.userService = userService;
        this.commentService = commentService;
        this.socialMediaService = socialMediaService;
    }
    
    @GetMapping(path = {"/", "/all"})
    @ApiOperation(value = "GET_ALL_USERS", nickname = "GET_ALL_USERS", 
            notes = "Get all Users", response = ResponseEntity.class)
    public ResponseEntity<APIResponse<PagedResources>> getAllUsers(@PageableDefault Pageable p, 
            PagedResourcesAssembler pagedAssembler) throws Throwable {
        logger.debug("Get all Users");
        return Optional.ofNullable(userService.findPaginated(p))
                .map(usersPage -> addLinksToUsers(usersPage))
                .map(usersPage -> pagedAssembler.toResource(usersPage))
                .map(usersPageResource -> ApiHelper.<PagedResources>createAndSendResponse(UserResponseCode.ALL_USERS, usersPageResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new ResourceNotFoundException(); });
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "GET_USER_BY_ID", nickname = "GET_USER_BY_ID", notes = "Get User By Id",
            response = ResponseEntity.class)
    public ResponseEntity<APIResponse<UserDTO>> getUserById(@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get User with id: " + id);
        return Optional.ofNullable(userService.getUserById(id))
                .map(userResource -> addLinksToUser(userResource))
                .map(userResource -> ApiHelper.<UserDTO>createAndSendResponse(UserResponseCode.SINGLE_USER, userResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new UserNotFoundException(); });
    }
    
    
    @GetMapping(path = "/{id}/comments")
    @ApiOperation(value = "GET_COMMENTS_BY_USER", nickname = "GET_COMMENTS_BY_USER", notes = "Get Comments By User Id",
            response = ResponseEntity.class)
    public ResponseEntity<APIResponse<PagedResources>> getCommentsByUserId(
            @PageableDefault Pageable p, 
            PagedResourcesAssembler pagedAssembler,
            @ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Comments by user with id: " + id);
        return Optional.ofNullable(commentService.getCommentByUserId(p, id))
                .map(commentsPage -> addLinksToComments(commentsPage))
                .map(commentsPage -> pagedAssembler.toResource(commentsPage))
                .map(commentsPageResource -> ApiHelper.<PagedResources>createAndSendResponse(CommentResponseCode.ALL_COMMENTS_BY_USER, commentsPageResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new CommentsByUserNotFoundException(); });
    }
    
    @GetMapping(path = "/{id}/social")
    @ApiOperation(value = "GET_SOCIAL_MEDIA_BY_USER_ID", nickname = "GET_SOCIAL_MEDIA_BY_USER_ID", notes = "Get Social Madia By User Id",
            response = ResponseEntity.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getSocialMediaByUserId(
            @ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Social Media by User Id " + id);
        return Optional.ofNullable(socialMediaService.getSocialMediaByUser(id))
                .map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
                .map(socialMediaResource -> ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_USER, socialMediaResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new SocialMediaNotFoundException(); });
    }
}
