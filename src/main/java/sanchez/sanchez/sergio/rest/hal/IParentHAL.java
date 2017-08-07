package sanchez.sanchez.sergio.rest.hal;


import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.rest.controller.ParentsController;
import sanchez.sanchez.sergio.rest.controller.UsersController;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author sergio
 */
public interface IParentHAL {
    
    default ParentDTO addLinksToParent(final ParentDTO parentResource) {
        Link selfLink = linkTo(UsersController.class).slash(parentResource.getIdentity()).withSelfRel();
        parentResource.add(selfLink);
        if(parentResource.getChildren() > 0) {
        	 try {
                 ResponseEntity<APIResponse<Iterable<SonDTO>>> methodLinkBuilder = methodOn(ParentsController.class)
                         .getChildrenOfParent(parentResource.getIdentity());
                 Link childrenLink = linkTo(methodLinkBuilder).withRel("children");
                 parentResource.add(childrenLink);
             } catch (Throwable ex) {
                 throw new RuntimeException(ex);
             }
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
