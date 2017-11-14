package es.bisite.usal.bulltect.web.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;

import es.bisite.usal.bulltect.web.dto.response.CommentDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaDTO;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import es.bisite.usal.bulltect.web.rest.controller.ChildrenController;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author sergio
 */
public interface ISonHAL {
    
    default SonDTO addLinksToSon(final SonDTO sonResource) {
        Link selfLink = linkTo(ChildrenController.class).slash(sonResource.getIdentity()).withSelfRel();
        sonResource.add(selfLink);
            try {
    
                ResponseEntity<APIResponse<Iterable<CommentDTO>>> methodLinkBuilder = methodOn(ChildrenController.class)
                        .getCommentsBySonId(sonResource.getIdentity());
                Link commentsLink = linkTo(methodLinkBuilder).withRel("comments");
                sonResource.add(commentsLink);
                
                ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> socialMediasLinkBuilder = methodOn(ChildrenController.class)
                        .getSocialMediaBySonId(sonResource.getIdentity());
                Link socialMediaLink = linkTo(socialMediasLinkBuilder).withRel("socialMedia");
                sonResource.add(socialMediaLink);
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }

        return sonResource;
    }

    default Iterable<SonDTO> addLinksToChildren(final Iterable<SonDTO> sonsResources) {
        for (SonDTO sonResource : sonsResources) {
        	addLinksToSon(sonResource);
        }
        return sonsResources;
    }
    
    default Page<SonDTO> addLinksToChildren(final Page<SonDTO> sonsPage) {
        for (SonDTO sonResource : sonsPage.getContent()) {
        	addLinksToSon(sonResource);
        }
        return sonsPage;
    }
}
