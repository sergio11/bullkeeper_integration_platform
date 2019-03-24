package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ISchoolService;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSchoolsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SchoolNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SchoolMustExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddSchoolDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SchoolNameDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ValidationErrorDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.ISchoolHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.SchoolResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForAdmin;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 
 * School Controller
 * @author sergiosanchezsanchez
 *
 */
@RestController("RestSchoolController")
@Validated
@RequestMapping("/api/v1/schools/")
@Api(tags = "schools", value = "/schools/", 
	description = "Administration of school information", 
	produces = "application/json")
public class SchoolController extends BaseController implements ISchoolHAL {

    private static Logger logger = LoggerFactory.getLogger(SchoolController.class);
    
    private final ISchoolService schoolService;

    /**
     * 
     * @param schoolService
     */
    public SchoolController(ISchoolService schoolService) {
        this.schoolService = schoolService;
    }
    
    /**
     * Get All Schools
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
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
    
    /**
     * Get Total Number Of School
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "GET_TOTAL_NUMBER_OF_SCHOOLS", nickname = "GET_TOTAL_NUMBER_OF_SCHOOLS", notes = "Get total number of schools",
            response = Long.class)
    public ResponseEntity<APIResponse<Long>> getTotalNumberOfSchools() throws Throwable {
    	
    	Long totalSchools = schoolService.getTotalNumberOfSchools();
    	
    	return ApiHelper.<Long>createAndSendResponse(SchoolResponseCode.TOTAL_NUMBER_OF_SCHOOLS, 
        		HttpStatus.OK, totalSchools);
    	
    }
    
    
    /**
     * Get All School Names
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/all/names", method = RequestMethod.GET)
    @ApiOperation(value = "GET_ALL_SCHOOL_NAMES", nickname = "GET_ALL_SCHOOL_NAMES", notes = "Get all School Names",
            response = SchoolNameDTO.class)
    public ResponseEntity<APIResponse<Iterable<SchoolNameDTO>>> getAllSchoolNames() throws Throwable {
    	
    	Iterable<SchoolNameDTO> schoolNames = schoolService.getAllSchoolNames();

    	if(Iterables.size(schoolNames) == 0)
    		throw new NoSchoolsFoundException();
    	
    	return ApiHelper.<Iterable<SchoolNameDTO>>createAndSendResponse(SchoolResponseCode.ALL_SCHOOLS_NAMES, HttpStatus.OK, schoolNames);
    	
    }
    
    
    /**
     * Get Schools By Name
     * @param name
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "FIND_SCHOOLS_BY_NAME", nickname = "FIND_SCHOOLS_BY_NAME", notes = "Find Schools by name",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<Iterable<SchoolDTO>>> getSchoolsByName(
    		@ApiParam(name="name", value = "name", required = true) 
    			@Valid @NotBlank(message = "{school.name.not.blank}") 
    				@RequestParam(value = "name", required = false) String name) throws Throwable {
    	
    	Iterable<SchoolDTO> schools = schoolService.findByName(name);
    	
    	if(Iterables.size(schools) == 0)
    		throw new NoSchoolsFoundException();
    	
    	return ApiHelper.<Iterable<SchoolDTO>>createAndSendResponse(SchoolResponseCode.SCHOOLS_BY_NAME, 
        		HttpStatus.OK, schools);
    }
    
    /**
     * Get School By ID
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "GET_SCHOOL_BY_ID", nickname = "GET_SCHOOL_BY_ID", notes = "Get School By Id")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "School By Id", response = SchoolDTO.class)
    })
    public ResponseEntity<APIResponse<SchoolDTO>> getSchoolById(
    		@ApiParam(name = "id", value = "Identificador del Centro escolar", required = true)
    			@Valid @ValidObjectId(message = "{id.not.valid}")
    		 		@PathVariable String id) throws Throwable {
        logger.debug("Get User with id: " + id);
        
        return Optional.ofNullable(schoolService.getSchoolById(id))
                .map(schoolResource -> addLinksToSchool(schoolResource))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SINGLE_SCHOOL, 
                		HttpStatus.OK, schoolResource))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
   
    /**
     * Save School
     * @param school
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "CREATE_SCHOOL", nickname = "CREATE_SCHOOL", notes = "Create School")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "School Saved", response = SchoolDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<SchoolDTO>> saveSchool(
    		@ApiParam(name="school", value = "school", required = true) 
				@Validated(ICommonSequence.class) @RequestBody AddSchoolDTO school) throws Throwable {        
        return Optional.ofNullable(schoolService.save(school))
                .map(schoolResource -> addLinksToSchool(schoolResource))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SCHOOL_SAVED, 
                		HttpStatus.OK, schoolResource))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
    
    
    /**
     * Delete School
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @OnlyAccessForAdmin
    @ApiOperation(value = "DELETE_SCHOOL", nickname = "DELETE_SCHOOL", notes = "Delete School")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "School Deleted", response = SchoolDTO.class)
    })
    public ResponseEntity<APIResponse<SchoolDTO>> deleteSchool(
    		@ApiParam(name = "id", value = "Identificador del Centro escolar", required = true)
    			@Valid @SchoolMustExists(message = "{school.not.exists}")
    		 		@PathVariable String id) throws Throwable {        
    	
        return Optional.ofNullable(schoolService.delete(id))
                .map(schoolResource -> ApiHelper.<SchoolDTO>createAndSendResponse(SchoolResponseCode.SCHOOL_DELETED, 
                		HttpStatus.OK, schoolResource))
                .orElseThrow(() -> { throw new SchoolNotFoundException(); });
    }
}
