package sanchez.sanchez.sergio.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;

import sanchez.sanchez.sergio.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.rest.controller.UsersController;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author sergio
 */
public interface ISonHAL {
    
    default SonDTO addLinksToSon(final SonDTO sonResource) {
        Link selfLink = linkTo(UsersController.class).slash(sonResource.getIdentity()).withSelfRel();
        sonResource.add(selfLink);
            try {
                ResponseEntity<APIResponse<PagedResources>> methodLinkBuilder = methodOn(UsersController.class)
                        .getCommentsByUserId(null, null, sonResource.getIdentity());
                Link commentsLink = linkTo(methodLinkBuilder).withRel("comments");
                sonResource.add(commentsLink);
                
                ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> socialMediasLinkBuilder = methodOn(UsersController.class)
                        .getSocialMediaByUserId(sonResource.getIdentity());
                Link socialMediaLink = linkTo(socialMediasLinkBuilder).withRel("socialMedia");
                sonResource.add(socialMediaLink);
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }

        return sonResource;
    }

    default Iterable<SonDTO> addLinksToSons(final Iterable<SonDTO> sonsResources) {
        for (SonDTO sonResource : sonsResources) {
        	addLinksToSon(sonResource);
        }
        return sonsResources;
    }
    
    default Page<SonDTO> addLinksToUsers(final Page<SonDTO> sonsPage) {
        for (SonDTO sonResource : sonsPage.getContent()) {
        	addLinksToSon(sonResource);
        }
        return sonsPage;
    }
}
