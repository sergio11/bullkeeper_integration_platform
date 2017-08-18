package sanchez.sanchez.sergio.rest.hal;


import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;

import sanchez.sanchez.sergio.dto.response.AlertDTO;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.rest.controller.AlertController;
import sanchez.sanchez.sergio.rest.controller.ParentsController;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author sergio
 */
public interface IParentHAL {
    
    default ParentDTO addLinksToParent(final ParentDTO parentResource) {
        Link selfLink = linkTo(ParentsController.class).slash(parentResource.getIdentity()).withSelfRel();
        parentResource.add(selfLink);
        try {
	        if(parentResource.getChildren() > 0) {
	        	ResponseEntity<APIResponse<Iterable<SonDTO>>> methodLinkBuilder = methodOn(ParentsController.class)
	        			.getChildrenOfParent(parentResource.getIdentity());
	            Link childrenLink = linkTo(methodLinkBuilder).withRel("children");
	            parentResource.add(childrenLink);
	         }
	        
	        ResponseEntity<APIResponse<SonDTO>> methodLinkBuilder = methodOn(ParentsController.class)
					.addSonToParent(parentResource.getIdentity(), null);
			
			Link addSonToParentLink = linkTo(methodLinkBuilder).withRel("addSonToParent");
			parentResource.add(addSonToParentLink);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
        return parentResource;
    }
    
	default ParentDTO addLinksToSelfParent(final ParentDTO parentResource) {
		Link selfLink = linkTo(ParentsController.class).slash("self").withSelfRel();
		parentResource.add(selfLink);
		try {
			if (parentResource.getChildren() > 0) {

				ResponseEntity<APIResponse<Iterable<SonDTO>>> methodLinkBuilder = methodOn(ParentsController.class)
						.getChildrenOfSelfParent(null);
				Link childrenLink = linkTo(methodLinkBuilder).withRel("children");
				parentResource.add(childrenLink);

			}
			
			ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> allAlertsLinkBuilder = methodOn(AlertController.class)
                    .getAllSelfAlerts(null, null, null);
            Link allAlertsLink = linkTo(allAlertsLinkBuilder).withRel("all_alerts");
            parentResource.add(allAlertsLink);
			
			ResponseEntity<APIResponse<SonDTO>> methodLinkBuilder = methodOn(ParentsController.class)
					.addSonToSelfParent(null, null);
			
			Link addSonToSelfLink = linkTo(methodLinkBuilder).withRel("addSonToSelf");
			parentResource.add(addSonToSelfLink);
			
			
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
		
		return parentResource;
	}

    default Iterable<ParentDTO> addLinksToParents(final Iterable<ParentDTO> parentResources) {
        for (ParentDTO parentResource : parentResources) {
        	addLinksToParent(parentResource);
        }
        return parentResources;
    }
    
    default Page<ParentDTO> addLinksToParents(final Page<ParentDTO> parentsPage) {
        for (ParentDTO parentResource : parentsPage.getContent()) {
        	addLinksToParent(parentResource);
        }
        return parentsPage;
    }
}
