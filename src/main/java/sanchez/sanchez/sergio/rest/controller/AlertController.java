package sanchez.sanchez.sergio.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sanchez.sanchez.sergio.dto.response.AlertDTO;
import sanchez.sanchez.sergio.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.NoAlertsFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.AlertResponseCode;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.security.utils.CurrentUser;
import sanchez.sanchez.sergio.service.IAlertService;
import springfox.documentation.annotations.ApiIgnore;

@Api
@RestController("RestAlertsController")
@Validated
@RequestMapping("/api/v1/alerts/")
public class AlertController {
	
	private static Logger logger = LoggerFactory.getLogger(AlertController.class);
	
	private final IAlertService alertService;
	
	public AlertController(IAlertService alertService) {
		super();
		this.alertService = alertService;
	}

	@GetMapping(path = { "/", "/all" } )
    @ApiOperation(value = "GET_ALERTS", nickname = "GET_ALERTS", notes="Get all alerts for the currently authenticated user", 
    	response = PagedResources.class)
	@PreAuthorize("@authorizationService.hasParentRole()")
	public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getAlerts(
    		@ApiIgnore @PageableDefault Pageable pageable,
    		PagedResourcesAssembler<AlertDTO> pagedAssembler,
    		@CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
		
		Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), pageable);
		
		if(alertsPage.getNumberOfElements() == 0) {
			throw new NoAlertsFoundException();
		}
		
		return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_ALERTS, 
        		HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }
	
	@GetMapping(path = "/info" )
    @ApiOperation(value = "GET_INFO_ALERTS", nickname = "GET_INFO_ALERTS", notes="Get all info alerts for the currently authenticated user", 
    	response = PagedResources.class)
	@PreAuthorize("@authorizationService.hasParentRole()")
	public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getInfoAlerts(
    		@ApiIgnore @PageableDefault Pageable pageable,
    		PagedResourcesAssembler<AlertDTO> pagedAssembler,
    		@CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
		
		Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), AlertLevelEnum.INFO, pageable);
		
		if(alertsPage.getNumberOfElements() == 0) {
			throw new NoAlertsFoundException();
		}
		
		return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_INFO_ALERTS, 
        		HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }
	
	@GetMapping(path = "/warning" )
    @ApiOperation(value = "GET_WARNING_ALERTS", nickname = "GET_WARNING_ALERTS", notes="Get warning alerts for the currently authenticated user", 
    	response = PagedResources.class)
	@PreAuthorize("@authorizationService.hasParentRole()")
	public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getWarningAlerts(
    		@ApiIgnore @PageableDefault Pageable pageable,
    		PagedResourcesAssembler<AlertDTO> pagedAssembler,
    		@CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
		
		Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), AlertLevelEnum.WARNING, pageable);
		
		if(alertsPage.getNumberOfElements() == 0) {
			throw new NoAlertsFoundException();
		}
		
		return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_WARNING_ALERTS, 
        		HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }
	
	@GetMapping(path = "/danger" )
    @ApiOperation(value = "GET_DANGER_ALERTS", nickname = "GET_DANGER_ALERTS", notes="Get danger alerts for the currently authenticated user",
    	response = PagedResources.class)
	@PreAuthorize("@authorizationService.hasParentRole()")
	public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getDangerAlerts(
    		@ApiIgnore @PageableDefault Pageable pageable,
    		PagedResourcesAssembler<AlertDTO> pagedAssembler,
    		@CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
		
		Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), AlertLevelEnum.DANGER, pageable);
		
		if(alertsPage.getNumberOfElements() == 0) {
			throw new NoAlertsFoundException();
		}
		
		return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_DANGER_ALERTS, 
        		HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }
	
	@GetMapping(path = "/success" )
    @ApiOperation(value = "GET_SUCCESS_ALERTS", nickname = "GET_SUCCESS_ALERTS", notes="Get success alerts for the currently authenticated user", 
    	response = PagedResources.class)
	@PreAuthorize("@authorizationService.hasParentRole()")
	public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getSuccessAlerts(
    		@ApiIgnore @PageableDefault Pageable pageable,
    		PagedResourcesAssembler<AlertDTO> pagedAssembler,
    		@CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
		
		Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), AlertLevelEnum.SUCCESS, pageable);
		
		if(alertsPage.getNumberOfElements() == 0) {
			throw new NoAlertsFoundException();
		}
		
		return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_DANGER_ALERTS, 
        		HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }
	
	@PostConstruct
	protected void init(){
		Assert.notNull(alertService, "Alert Service cannot be a null");
	}
}
