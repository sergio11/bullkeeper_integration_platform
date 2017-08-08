package sanchez.sanchez.sergio.rest.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.ParentNotFoundException;
import sanchez.sanchez.sergio.rest.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.rest.exception.UserNotFoundException;
import sanchez.sanchez.sergio.rest.hal.IParentHAL;
import sanchez.sanchez.sergio.rest.hal.ISonHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.ParentResponseCode;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.security.utils.CurrentUser;
import sanchez.sanchez.sergio.service.IParentsService;

@Api
@RestController("RestParentsController")
@RequestMapping("/api/v1/parents/")
public class ParentsController implements IParentHAL, ISonHAL {

    private static Logger logger = LoggerFactory.getLogger(ParentsController.class);
    
    private final IParentsService parentsService;
 

    public ParentsController(IParentsService parentsService) {
        this.parentsService = parentsService;
    }
    
    @GetMapping(path = {"/", "/all"})
    @ApiOperation(value = "GET_ALL_PARENTS", nickname = "GET_ALL_PARENTS", 
            notes = "Get all Parents")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "", response = PagedResources.class)
    })
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
            notes = "Get Parent By Id")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Parent By Id", response = ParentDTO.class),
    		@ApiResponse(code = 404, message= "Parent Not Found")
    })
    public ResponseEntity<APIResponse<ParentDTO>> getParentById(
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Parent with id: " + id);
        return Optional.ofNullable(parentsService.getParentById(id))
                .map(parentResource -> addLinksToParent(parentResource))
                .map(parentResource -> ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.SINGLE_PARENT, parentResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new UserNotFoundException(); });
    }
    
    @GetMapping(path = "/self")
    @ApiOperation(value = "GET_PARENT_SELF_INFORMATION", nickname = "GET_PARENT_SELF_INFORMATION", notes = "Get information from the currently authenticated parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Self Parent", response = ParentDTO.class),
    		@ApiResponse(code = 404, message= "Parent Not Found")
    })
    @PreAuthorize("@authorizationService.hasParentRole()")
    public ResponseEntity<APIResponse<ParentDTO>> getSelfInformation(@CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
        logger.debug("Get Information for Parent with id: " + selfParent.getUserId());
        return Optional.ofNullable(parentsService.getParentById(selfParent.getUserId()))
                .map(parentResource -> addLinksToParent(parentResource))
                .map(parentResource -> ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.SELF_PARENT, parentResource, HttpStatus.OK))
                .orElseThrow(() -> {
                    throw new ParentNotFoundException();
                });
    }
    
    
    @PostMapping(path = "/")
    @ApiOperation(value = "REGISTER_PARENT", nickname = "REGISTER_PARENT", notes="Register Parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Register Parent", response = ParentDTO.class)
    })
    public ResponseEntity<APIResponse<ParentDTO>> registerParent(
    		@ApiParam(value = "parent", required = true) 
    			@Valid @RequestBody RegisterParentDTO registerParentDTO) throws Throwable {
    	logger.debug("Register Parent");
        return Optional.ofNullable(parentsService.save(registerParentDTO))
        		.map(parentResource -> addLinksToParent(parentResource))
        		.map(parentResource -> ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.PARENT_REGISTERED_SUCCESSFULLY, parentResource, HttpStatus.OK))
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException();
                });
    }
    
    @GetMapping(path = {"/{id}/children"})
    @ApiOperation(value = "GET_CHILDREN_OF_PARENT", nickname = "GET_CHILDREN_OF_PARENT", 
            notes = "Get Children of Parent", response = ResponseEntity.class)
    public ResponseEntity<APIResponse<Iterable<SonDTO>>> getChildrenOfParent(
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get Children of Parent with id: " + id);
        return Optional.ofNullable(parentsService.getChildrenOfParent(id))
                .map(sonsResources -> addLinksToChildren(sonsResources))
                .map(sonsResources -> ApiHelper.<Iterable<SonDTO>>createAndSendResponse(ParentResponseCode.CHILDREN_OF_PARENT, sonsResources, HttpStatus.OK))
                .orElseThrow(() -> { throw new UserNotFoundException(); });
    }
    
    @GetMapping(path = {"/self/children"})
    @ApiOperation(value = "GET_CHILDREN_OF_SELF_PARENT", nickname = "GET_CHILDREN_OF_SELF_PARENT", 
            notes = "Get Children for the currently authenticated parent", response = ResponseEntity.class)
    @PreAuthorize("@authorizationService.hasParentRole()")
    public ResponseEntity<APIResponse<Iterable<SonDTO>>> getChildrenOfSelfParent(
    		@CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
        logger.debug("Get Children of Self Parent");
        return Optional.ofNullable(parentsService.getChildrenOfParent(selfParent.getUserId().toString()))
                .map(sonsResources -> addLinksToChildren(sonsResources))
                .map(sonsResources -> ApiHelper.<Iterable<SonDTO>>createAndSendResponse(ParentResponseCode.CHILDREN_OF_PARENT, sonsResources, HttpStatus.OK))
                .orElseThrow(() -> { throw new UserNotFoundException(); });
    }
    
 
    @PutMapping(path = "/{id}/children/add")
    @ApiOperation(value = "ADD_SON_TO_PARENT", nickname = "ADD_SON_TO_PARENT", notes="Add son to parent for analysis", response = ResponseEntity.class)
    public ResponseEntity<APIResponse<SonDTO>> addSonToParent(
    		@ApiParam(value = "id", required = true) @PathVariable String id,
    		@ApiParam(value = "son", required = true) 
    			@Valid @RequestBody RegisterSonDTO registerSonDTO) throws Throwable {
    	logger.debug("Add Son To Parent");
    	return Optional.ofNullable(parentsService.addSon(id, registerSonDTO))
        		.map(sonResource -> addLinksToSon(sonResource))
        		.map(sonResource -> ApiHelper.<SonDTO>createAndSendResponse(ParentResponseCode.ADDED_SON_TO_PARENT, sonResource, HttpStatus.OK))
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException();
                });
    }
    
    @PutMapping(path = "/self/children/add")
    @ApiOperation(value = "ADD_SON_TO_SELF_PARENT", nickname = "ADD_SON_TO_SELF_PARENT", notes="Add son to currently authenticated parent for analysis", 
    	response = ResponseEntity.class)
    @PreAuthorize("@authorizationService.hasParentRole()")
    public ResponseEntity<APIResponse<SonDTO>> addSonToSelfParent(
    		@CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
    		@ApiParam(value = "son", required = true) 
    			@Valid @RequestBody RegisterSonDTO registerSonDTO) throws Throwable {
    	logger.debug("Add Son To Self Parent");
    	return Optional.ofNullable(parentsService.addSon(selfParent.getUserId().toString(), registerSonDTO))
        		.map(sonResource -> addLinksToSon(sonResource))
        		.map(sonResource -> ApiHelper.<SonDTO>createAndSendResponse(ParentResponseCode.ADDED_SON_TO_SELF_PARENT, sonResource, HttpStatus.OK))
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException();
                });
    }
    
}
