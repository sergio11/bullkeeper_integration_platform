package es.bisite.usal.bulltect.web.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import es.bisite.usal.bulltect.web.dto.response.AlertDTO;
import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.dto.response.ParentDTO;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import es.bisite.usal.bulltect.web.rest.controller.AlertController;
import es.bisite.usal.bulltect.web.rest.controller.ParentsController;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
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
            if (parentResource.getChildren() > 0) {
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
            // All Alerts
            ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> allAlertsLinkBuilder = methodOn(AlertController.class)
                    .getAllSelfAlerts(null, null, null, null);
            Link allAlertsLink = linkTo(allAlertsLinkBuilder).withRel("all_alerts");
            parentResource.add(allAlertsLink);

            // Add Son
            ResponseEntity<APIResponse<SonDTO>> methodLinkBuilder = methodOn(ParentsController.class)
                    .addSonToSelfParent(null, null);

            Link addSonToSelfLink = linkTo(methodLinkBuilder).withRel("addSonToSelf");
            parentResource.add(addSonToSelfLink);
            
            // Self Profile Image
            ResponseEntity<byte[]> downloadProfileImageLinkBuilder = methodOn(ParentsController.class)
                    .downloadProfileImage(null);

            Link downloadProfileImageLink = linkTo(downloadProfileImageLinkBuilder).withRel("profile_image");
            parentResource.add(downloadProfileImageLink);
            
            // Self Upload Profile
            ResponseEntity<APIResponse<ImageDTO>> uploadProfileImageForSelfUserLinkBuilder = methodOn(ParentsController.class)
                    .uploadProfileImageForSelfUser(null, null);
            
            Link uploadProfileImageLink = linkTo(uploadProfileImageForSelfUserLinkBuilder).withRel("upload_profile_image");
            parentResource.add(uploadProfileImageLink);
            
            
            // Self Delete Profile Image
            
            ResponseEntity<APIResponse<String>> selfDeleteProfileImageLinkBuilder = methodOn(ParentsController.class)
                    .deleteProfileImage(null);
            
            Link deleteProfileImageLink = linkTo(selfDeleteProfileImageLinkBuilder).withRel("delete_profile_image");
            parentResource.add(deleteProfileImageLink);

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
