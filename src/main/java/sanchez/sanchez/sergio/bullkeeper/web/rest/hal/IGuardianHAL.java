package sanchez.sanchez.sergio.bullkeeper.web.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SupervisedChildrenDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.AlertController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.GuardiansController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author sergio
 */
public interface IGuardianHAL {

    default GuardianDTO addLinksToParent(final GuardianDTO guardianResource) {
    	
        Link selfLink = linkTo(GuardiansController.class).slash(guardianResource.getIdentity())
        		.withSelfRel();
        
        guardianResource.add(selfLink);
        try {
            if (guardianResource.getChildren() > 0) {
                ResponseEntity<APIResponse<Iterable<SupervisedChildrenDTO>>> methodLinkBuilder = 
                		methodOn(GuardiansController.class)
                        .getChildrenOfGuardian(guardianResource.getIdentity());
                
                Link childrenLink = linkTo(methodLinkBuilder).withRel("children");
                guardianResource.add(childrenLink);
            }

            ResponseEntity<APIResponse<KidDTO>> methodLinkBuilder = methodOn(GuardiansController.class)
                    .addKidToGuardian(guardianResource.getIdentity(), null);

            Link addKidToGuardianLink = linkTo(methodLinkBuilder).withRel("addKidToGuardian");
            guardianResource.add(addKidToGuardianLink);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
        return guardianResource;
    }

    default GuardianDTO addLinksToSelfGuardian(final GuardianDTO guardianResource) {
        Link selfLink = linkTo(GuardiansController.class).slash("self").withSelfRel();
        guardianResource.add(selfLink);
        try {
            if (guardianResource.getChildren() > 0) {

                ResponseEntity<APIResponse<Iterable<SupervisedChildrenDTO>>> methodLinkBuilder = 
                		methodOn(GuardiansController.class)
                        .getChildrenOfSelfGuardian(null);
                Link childrenLink = linkTo(methodLinkBuilder).withRel("children");
                guardianResource.add(childrenLink);

            }
            // All Alerts
            ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> allAlertsLinkBuilder = methodOn(AlertController.class)
                    .getAllSelfAlerts(null, null, null, null);
            Link allAlertsLink = linkTo(allAlertsLinkBuilder).withRel("all_alerts");
            guardianResource.add(allAlertsLink);

            // Add Son
            ResponseEntity<APIResponse<KidDTO>> methodLinkBuilder = methodOn(GuardiansController.class)
                    .addKidToSelfGuardian(null, null);

            Link addKidToSelfLink = linkTo(methodLinkBuilder).withRel("addKidToSelf");
            guardianResource.add(addKidToSelfLink);
            
            // Self Profile Image
            ResponseEntity<byte[]> downloadProfileImageLinkBuilder = methodOn(GuardiansController.class)
                    .downloadProfileImage(null);

            Link downloadProfileImageLink = linkTo(downloadProfileImageLinkBuilder)
            		.withRel("profile_image");
            guardianResource.add(downloadProfileImageLink);
            
            // Self Upload Profile
            ResponseEntity<APIResponse<ImageDTO>> uploadProfileImageForSelfUserLinkBuilder = 
            		methodOn(GuardiansController.class)
                    .uploadProfileImageForSelfUser(null, null);
            
            Link uploadProfileImageLink = linkTo(uploadProfileImageForSelfUserLinkBuilder)
            		.withRel("upload_profile_image");
            guardianResource.add(uploadProfileImageLink);
            
            
            // Self Delete Profile Image
            
            ResponseEntity<APIResponse<String>> selfDeleteProfileImageLinkBuilder =
            		methodOn(GuardiansController.class)
                    .deleteProfileImage(null);
            
            Link deleteProfileImageLink = linkTo(selfDeleteProfileImageLinkBuilder).withRel("delete_profile_image");
            guardianResource.add(deleteProfileImageLink);

        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }

        return guardianResource;
    }

    default Iterable<GuardianDTO> addLinksToGuardians(final Iterable<GuardianDTO> guardiansResources) {
        for (GuardianDTO guardianResource : guardiansResources) {
            addLinksToParent(guardianResource);
        }
        return guardiansResources;
    }

    default Page<GuardianDTO> addLinksToGuardian(final Page<GuardianDTO> guardianPage) {
        for (GuardianDTO parentResource : guardianPage.getContent()) {
            addLinksToParent(parentResource);
        }
        return guardianPage;
    }
}
