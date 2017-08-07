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
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.UserDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.rest.exception.UserNotFoundException;
import sanchez.sanchez.sergio.rest.hal.IParentHAL;
import sanchez.sanchez.sergio.rest.hal.IUserHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.ParentResponseCode;
import sanchez.sanchez.sergio.service.IParentsService;

@Api
@RestController("RestParentsController")
@RequestMapping("/api/v1/parents/")
public class ParentsController implements IParentHAL, IUserHAL{

    private static Logger logger = LoggerFactory.getLogger(ParentsController.class);
    
    private final IParentsService parentsService;

    public ParentsController(IParentsService parentsService) {
        this.parentsService = parentsService;
    }
    
    @GetMapping(path = {"/", "/all"})
    @ApiOperation(value = "GET_ALL_PARENTS", nickname = "GET_ALL_PARENTS", 
            notes = "Get all Parents", response = ResponseEntity.class)
    public ResponseEntity<APIResponse<PagedResources>> getAllParents(@PageableDefault Pageable p, 
            PagedResourcesAssembler pagedAssembler) throws Throwable {
        logger.debug("Get all Parents");
        return Optional.ofNullable(parentsService.findPaginated(p))
                .map(parentsPage -> addLinksToParents(parentsPage))
                .map(parentsPage -> pagedAssembler.toResource(parentsPage))
                .map(parentsPageResource -> ApiHelper.<PagedResources>createAndSendResponse(ParentResponseCode.ALL_PARENTS, parentsPageResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new ResourceNotFoundException(); });
    }
    
    
    @GetMapping(path = {"/{id}"})
    @ApiOperation(value = "GET_PARENT_BY_ID", nickname = "GET_PARENT_BY_ID", 
            notes = "Get Parent By Id", response = ResponseEntity.class)
    public ResponseEntity<APIResponse<ParentDTO>> getParentById(
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Parent with id: " + id);
        return Optional.ofNullable(parentsService.getParentById(id))
                .map(parentResource -> addLinksToParent(parentResource))
                .map(parentResource -> ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.SINGLE_PARENT, parentResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new UserNotFoundException(); });
    }
    
    
    @GetMapping(path = {"/{id}/children"})
    @ApiOperation(value = "GET_CHILDREN_OF_PARENT", nickname = "GET_CHILDREN_OF_PARENT", 
            notes = "Get Children of Parent", response = ResponseEntity.class)
    public ResponseEntity<APIResponse<Iterable<UserDTO>>> getChildrenOfParent(
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Children of Parent with id: " + id);
        return Optional.ofNullable(parentsService.getChildrenOfParent(id))
                .map(childrenOfParentResource -> addLinksToUsers(childrenOfParentResource))
                .map(childrenOfParentResource -> ApiHelper.<Iterable<UserDTO>>createAndSendResponse(ParentResponseCode.CHILDREN_OF_PARENT, childrenOfParentResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new UserNotFoundException(); });
    }
    
    
    
    
}
