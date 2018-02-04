package es.bisite.usal.bulltect.web.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;

import es.bisite.usal.bulltect.web.dto.response.AlertDTO;
import es.bisite.usal.bulltect.web.rest.controller.AlertController;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author sergio
 */
public interface IAlertHAL {
    
    default AlertDTO addLinksToAlert(final AlertDTO alertResource) {
            try {
    
            	// All Alerts
            	ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> allAlertsLinkBuilder = methodOn(AlertController.class)
                        .getAllSelfAlerts(null, null, null, null);
                Link allAlertsLink = linkTo(allAlertsLinkBuilder).withRel("all_alerts");
                alertResource.add(allAlertsLink);
                // Info Alerts
                ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> infoAlertsLinkBuilder = methodOn(AlertController.class)
                        .getInfoAlerts(null, null, null, null);
                Link infoAlertsLink = linkTo(infoAlertsLinkBuilder).withRel("info_alerts");
                alertResource.add(infoAlertsLink);
                // Warning Alerts
                ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> warningAlertsLinkBuilder = methodOn(AlertController.class)
                        .getWarningAlerts(null, null, null, null);
                Link warningAlertsLink = linkTo(warningAlertsLinkBuilder).withRel("warning_alerts");
                alertResource.add(warningAlertsLink);
                // Danger Alerts
                ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> dangerAlertsLinkBuilder = methodOn(AlertController.class)
                        .getDangerAlerts(null, null, null, null);
                Link dangerAlertsLink = linkTo(dangerAlertsLinkBuilder).withRel("danger_alerts");
                alertResource.add(dangerAlertsLink);
                // Success Alerts
                ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> successAlertsLinkBuilder = methodOn(AlertController.class)
                        .getSuccessAlerts(null, null, null, null);
                Link successAlertsLink = linkTo(successAlertsLinkBuilder).withRel("success_alerts");
                alertResource.add(successAlertsLink);
                
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }

        return alertResource;
    }

    default Iterable<AlertDTO> addLinksToChildren(final Iterable<AlertDTO> alertsResources) {
        for (AlertDTO alertResource : alertsResources) {
        	addLinksToAlert(alertResource);
        }
        return alertsResources;
    }
    
    default Page<AlertDTO> addLinksToChildren(final Page<AlertDTO> alertPage) {
        for (AlertDTO alertResource : alertPage.getContent()) {
        	addLinksToAlert(alertResource);
        }
        return alertPage;
    }
}
