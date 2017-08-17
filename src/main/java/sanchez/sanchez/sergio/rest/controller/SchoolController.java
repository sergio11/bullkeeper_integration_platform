package sanchez.sanchez.sergio.rest.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sanchez.sanchez.sergio.dto.request.AddSchoolDTO;
import sanchez.sanchez.sergio.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.dto.response.ValidationErrorDTO;
import sanchez.sanchez.sergio.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.NoSchoolsFoundException;
import sanchez.sanchez.sergio.rest.exception.SchoolNotFoundException;
import sanchez.sanchez.sergio.rest.hal.ISchoolHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.SchoolResponseCode;
import sanchez.sanchez.sergio.service.ISchoolService;
import springfox.documentation.annotations.ApiIgnore;

@Api
@RestController("RestSchoolController")
@Validated
@RequestMapping("/api/v1/schools/")
public class SchoolController implements ISchoolHAL {

    private static Logger logger = LoggerFactory.getLogger(SchoolController.class);
    
    private final ISchoolService schoolService;

    public SchoolController(ISchoolService schoolService) {
        this.schoolService = schoolService;
    }
    
    @GetMapping(path = {"/all"})
    @ApiOperation(value = "GET_ALL_SCHOOLS", nickname = "GET_ALL_SCHOOLS", notes = "Get all Schools",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<SchoolDTO>>>> getAllSchools(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<SchoolDTO> pagedAssembler) throws Throwable {
    	
    	Page<SchoolDTO> schoolPage = schoolService.findPaginated(pageable);

    	if(schoolPage.getTotalElements() == 0)
    		throw new NoSchoolsFoundException();
    	
    	return ApiHelper.<PagedResources<Resource<SchoolDTO>>>createAndSendResponse(SchoolResponseCode.ALL_SCHOOLS, 
        		HttpStatus.OK, pagedAssembler.toResource(addLinksToSchool((schoolPage))));
    	
    }
    
    @GetMapping(path = "/")
    @ApiOperation(value = "FIND_SCHOOLS_BY_NAME", nickname = "FIND_SCHOOLS_BY_NAME", notes = "Find Schools by name",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<SchoolDTO>>>> getSchoolsByName(
    		@Valid @NotBlank(message = "{school.name.notblank}") 
    		@RequestParam(value = "name", required = false) String name,
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<SchoolDTO> pagedAssembler) throws Throwable {
    	
    	Page<SchoolDTO> schoolPage = schoolService.findByNamePaginated(name, pageable);
    	
    	if(schoolPage.getTotalElements() == 0)
    		throw new NoSchoolsFoundException();
    	
    	return ApiHelper.<PagedResources<Resource<SchoolDTO>>>createAndSendResponse(SchoolResponseCode.SCHOOLS_BY_NAME, 
        		HttpStatus.OK, pagedAssembler.toResource(addLinksToSchool((schoolPage))));
    }
    
    
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "GET_SCHOOL_BY_ID", nickname = "GET_SCHOOL_BY_ID", notes = "Get School By Id")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "School By Id", response = SchoolDTO.class)
    })
    public ResponseEntity<APIResponse<SchoolDTO>> getSchoolById(
    		@Valid @ValidObjectId(message = "{school.id.notvalid}")
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get User with id: " + id);
        
        return Optional.ofNullable(schoolService.getSchoolById(id))
                .map(schoolResource -> addLinksToSchool(schoolResource))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SINGLE_SCHOOL, 
                		HttpStatus.OK, schoolResource))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
    
    @PutMapping(path = "/")
    @ApiOperation(value = "CREATE_SCHOOL", nickname = "CREATE_SCHOOL", notes = "Create School")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "School Saved", response = SchoolDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    @PreAuthorize("@authorizationService.hasAdminRole()")
    public ResponseEntity<APIResponse<SchoolDTO>> saveSchool(
    		@ApiParam(value = "school", required = true) 
				@Valid @RequestBody AddSchoolDTO school) throws Throwable {        
        return Optional.ofNullable(schoolService.save(school))
                .map(schoolResource -> addLinksToSchool(schoolResource))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SCHOOL_SAVED, 
                		HttpStatus.OK, schoolResource))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
    
    
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "DELETE_SCHOOL", nickname = "DELETE_SCHOOL", notes = "Delete School")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "School Deleted", response = SchoolDTO.class)
    })
    @PreAuthorize("@authorizationService.hasAdminRole()")
    public ResponseEntity<APIResponse<SchoolDTO>> deleteSchool(
    		@Valid @ValidObjectId(message = "{school.id.notvalid}")
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {        
    	
        return Optional.ofNullable(schoolService.delete(id))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SCHOOL_DELETED, 
                		HttpStatus.OK, schoolResource))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
}
