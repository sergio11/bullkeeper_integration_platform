package sanchez.sanchez.sergio.bullkeeper.web.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SupervisedChildrenDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.ChildrenController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author sergio
 */
public interface IKidHAL {
    
	
	/**
	 * Add Links To Kid
	 * @param kidResource
	 * @return
	 */
    default KidDTO addLinksToKid(final KidDTO kidResource) {
        Link selfLink = linkTo(ChildrenController.class)
        		.slash(kidResource.getIdentity()).withSelfRel();
        kidResource.add(selfLink);
            try {
    
                ResponseEntity<APIResponse<Iterable<CommentDTO>>> methodLinkBuilder = methodOn(ChildrenController.class)
                        .getCommentsByKidId(kidResource.getIdentity(), null, null, null, null, null, null , null);
                Link commentsLink = linkTo(methodLinkBuilder).withRel("comments");
                kidResource.add(commentsLink);
                
                ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> socialMediasLinkBuilder = methodOn(ChildrenController.class)
                        .getSocialMediaByKidId(kidResource.getIdentity());
                Link socialMediaLink = linkTo(socialMediasLinkBuilder).withRel("socialMedia");
                kidResource.add(socialMediaLink);
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }

        return kidResource;
    }

    /**
     * Add Links To Supervised Children
     * @param supervisedChildrenListResource
     * @return
     */
    default Iterable<SupervisedChildrenDTO> addLinksToSupervisedChildren(final Iterable<SupervisedChildrenDTO> supervisedChildrenListResource) {
        for (final SupervisedChildrenDTO supervisedChildrenResource : supervisedChildrenListResource) {
        	addLinksToKid(supervisedChildrenResource.getKid());
        }
        return supervisedChildrenListResource;
    }
    
    /**
     * Add Links To Supervised Children
     * @param supervisedChildrenPage
     * @return
     */
    default Page<SupervisedChildrenDTO> addLinksToSupervisedChildren(final Page<SupervisedChildrenDTO> supervisedChildrenPage) {
        for (final SupervisedChildrenDTO supervisedChildrenResource : supervisedChildrenPage.getContent()) {
        	addLinksToKid(supervisedChildrenResource.getKid());
        }
        return supervisedChildrenPage;
    }
    
    
    /**
     * Add Links To Children
     * @param childrenResources
     * @return
     */
   default Iterable<KidDTO> addLinksToChildren(final Iterable<KidDTO> childrenResources) {
        for (final KidDTO kidResource : childrenResources) {
        	addLinksToKid(kidResource);
        }
        return childrenResources;
    }
    
    /**
     * Add Links To Children
     * @param childrenPage
     * @return
     */
    default Page<KidDTO> addLinksToChildren(final Page<KidDTO> childrenPage) {
        for (final KidDTO kidResource : childrenPage.getContent()) {
        	addLinksToKid(kidResource);
        }
        return childrenPage;
    }
}
