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
import sanchez.sanchez.sergio.dto.UserDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.rest.exception.UserNotFoundException;
import sanchez.sanchez.sergio.rest.hal.IUserHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.UserResponseCode;
import sanchez.sanchez.sergio.service.IUserService;

@Api
@RestController
@RequestMapping("/api/v1/users/")
public class UsersController implements IUserHAL {

    private static Logger logger = LoggerFactory.getLogger(UsersController.class);
    
    private final IUserService userService;

    public UsersController(IUserService userService) {
        this.userService = userService;
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
    public ResponseEntity<APIResponse<UserDTO>> getAnalystById(@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get User with id: " + id);
        return Optional.ofNullable(userService.getUserById(id))
                .map(userResource -> addLinksToUser(userResource))
                .map(userResource -> ApiHelper.<UserDTO>createAndSendResponse(UserResponseCode.SINGLE_USER, userResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new UserNotFoundException(); });
    }
}
