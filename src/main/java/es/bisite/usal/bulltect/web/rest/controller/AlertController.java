package es.bisite.usal.bulltect.web.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;
import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.persistence.constraints.group.ICommonSequence;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.web.dto.request.AddAlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertDTO;
import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.exception.NoAlertsFoundException;
import es.bisite.usal.bulltect.web.rest.exception.SocialMediaNotFoundException;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.AlertResponseCode;
import es.bisite.usal.bulltect.web.security.userdetails.CommonUserDetailsAware;
import es.bisite.usal.bulltect.web.security.utils.CurrentUser;
import es.bisite.usal.bulltect.web.security.utils.OnlyAccessForAdmin;
import es.bisite.usal.bulltect.web.security.utils.OnlyAccessForParent;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController("RestAlertsController")
@Validated
@RequestMapping("/api/v1/alerts/")
@Api(tags = "alerts", value = "/alerts/", description = "Punto de entrada para el manejo de Alertas", produces = "application/json")
public class AlertController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AlertController.class);

    private final IAlertService alertService;

    public AlertController(IAlertService alertService) {
        super();
        this.alertService = alertService;
    }

    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_ALERT", nickname = "GET_ALL_ALERT", notes = "Get all alerts in the system",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getAllAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        final Page<AlertDTO> alertsPage = alertService.findPaginated(delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    @RequestMapping(value = {"/self"}, method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_ALL_SELF_ALERT_PAGINATED", nickname = "GET_ALL_SELF_ALERT_PAGINATED", notes = "Get all alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getAllSelfAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_SELF_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    @RequestMapping(value = {"/self/all"}, method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_ALL_SELF_ALERT", nickname = "GET_ALL_SELF_ALERT", notes = "Get all alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getAllSelfAlerts(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Iterable<AlertDTO> alertsPage = alertService.findByParent(selfParent.getUserId(), delivered);

        if (Iterables.size(alertsPage) == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(AlertResponseCode.ALL_SELF_ALERTS,
                HttpStatus.OK, alertsPage);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "CREATE_ALERT", nickname = "CREATE_ALERT", notes = "Create Alert",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<AlertDTO>> addAlert(
            @ApiParam(value = "alert", required = true)
            @Validated(ICommonSequence.class) @RequestBody AddAlertDTO alert,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {
        return Optional.ofNullable(alertService.save(alert))
                .map(alertResource -> ApiHelper.<AlertDTO>createAndSendResponse(AlertResponseCode.ALERT_CREATED, HttpStatus.OK, alertResource))
                .orElseThrow(() -> {
                    throw new SocialMediaNotFoundException();
                });
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_INFO_ALERTS", nickname = "GET_INFO_ALERTS", notes = "Get all info alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getInfoAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), AlertLevelEnum.INFO, delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_INFO_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    @RequestMapping(value = "/warning", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_WARNING_ALERTS", nickname = "GET_WARNING_ALERTS", notes = "Get warning alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getWarningAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), AlertLevelEnum.WARNING, delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_WARNING_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    @RequestMapping(value = "/danger", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_DANGER_ALERTS", nickname = "GET_DANGER_ALERTS", notes = "Get danger alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getDangerAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), AlertLevelEnum.DANGER, delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_DANGER_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_SUCCESS_ALERTS", nickname = "GET_SUCCESS_ALERTS", notes = "Get success alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getSuccessAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByParentPaginated(selfParent.getUserId(), AlertLevelEnum.SUCCESS, delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_DANGER_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }
    
    
    

    @PostConstruct
    protected void init() {
        Assert.notNull(alertService, "Alert Service cannot be a null");
    }
}
