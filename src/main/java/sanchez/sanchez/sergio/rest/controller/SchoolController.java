package sanchez.sanchez.sergio.rest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import sanchez.sanchez.sergio.dto.request.AddSchoolDTO;
import sanchez.sanchez.sergio.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.SchoolNotFoundException;
import sanchez.sanchez.sergio.rest.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.rest.hal.ISchoolHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.SchoolResponseCode;
import sanchez.sanchez.sergio.service.ISchoolService;

@Api
@RestController("RestSchoolController")
@RequestMapping("/api/v1/schools/")
public class SchoolController implements ISchoolHAL {

    private static Logger logger = LoggerFactory.getLogger(SchoolController.class);
    
    private final ISchoolService schoolService;

    public SchoolController(ISchoolService schoolService) {
        this.schoolService = schoolService;
    }
    
    @GetMapping(path = {"/" , "/all"})
    @ApiOperation(value = "GET_ALL_SCHOOLS", nickname = "GET_ALL_SCHOOLS", notes = "Get all Schools",
            response = ResponseEntity.class)
    public ResponseEntity<APIResponse<PagedResources>> getAllSchools(
    		@PageableDefault Pageable p, 
            PagedResourcesAssembler pagedAssembler) throws Throwable {
    	
        return Optional.ofNullable(schoolService.findPaginated(p))
                .map(schoolPage -> addLinksToSchool(schoolPage))
                .map(schoolPage -> pagedAssembler.toResource(schoolPage))
                .map(schoolPage -> ApiHelper.<PagedResources>createAndSendResponse(SchoolResponseCode.ALL_SCHOOLS, schoolPage, HttpStatus.OK))
                .orElseThrow(() -> { throw new SocialMediaNotFoundException(); });
    }
    
    
    @GetMapping(path = "/")
    @ApiOperation(value = "GET_SCHOOLS_BY_NAME", nickname = "GET_SCHOOLS_BY_NAME", notes = "Get Schools by name",
            response = ResponseEntity.class)
    public ResponseEntity<APIResponse<PagedResources>> getSchoolsByName(
    		@RequestParam(value = "name", required = false) String name,
    		@PageableDefault Pageable pageable, 
            PagedResourcesAssembler pagedAssembler) throws Throwable {
    	
        return Optional.ofNullable(schoolService.findByNamePaginated(name, pageable))
                .map(schoolPage -> addLinksToSchool(schoolPage))
                .map(schoolPage -> pagedAssembler.toResource(schoolPage))
                .map(schoolPage -> ApiHelper.<PagedResources>createAndSendResponse(SchoolResponseCode.SCHOOLS_BY_NAME, schoolPage, HttpStatus.OK))
                .orElseThrow(() -> { throw new SocialMediaNotFoundException(); });
    }
    
    
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "GET_SCHOOL_BY_ID", nickname = "GET_SCHOOL_BY_ID", notes = "Get School By Id",
            response = ResponseEntity.class)
    public ResponseEntity<APIResponse<SchoolDTO>> getSchoolById(
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {
        logger.debug("Get User with id: " + id);
        
        return Optional.ofNullable(schoolService.getSchoolById(id))
                .map(schoolResource -> addLinksToSchool(schoolResource))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SINGLE_SCHOOL, schoolResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
    
    @PutMapping(path = "/")
    @ApiOperation(value = "CREATE_SCHOOL", nickname = "CREATE_SCHOOL", notes = "Create School",
    	response = ResponseEntity.class)
    public ResponseEntity<APIResponse<SchoolDTO>> saveSchool(
    		@ApiParam(value = "school", required = true) 
				@Valid @RequestBody AddSchoolDTO addSchoolDTO) throws Throwable {        
    	
        return Optional.ofNullable(schoolService.save(addSchoolDTO))
                .map(schoolResource -> addLinksToSchool(schoolResource))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SCHOOL_SAVED, schoolResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
    
    
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "DELETE_SCHOOL", nickname = "DELETE_SCHOOL", notes = "Delete School",
		response = ResponseEntity.class)
    public ResponseEntity<APIResponse<SchoolDTO>> deleteSchool(
    		@ApiParam(value = "id", required = true) @PathVariable String id) throws Throwable {        
    	
        return Optional.ofNullable(schoolService.delete(id))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SCHOOL_DELETED, schoolResource, HttpStatus.OK))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
   
}
