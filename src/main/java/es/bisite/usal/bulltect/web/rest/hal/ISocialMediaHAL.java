package es.bisite.usal.bulltect.web.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;

import es.bisite.usal.bulltect.web.dto.response.SocialMediaDTO;
import es.bisite.usal.bulltect.web.rest.controller.SocialMediaController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
/**
 *
 * @author sergio
 */
public interface ISocialMediaHAL {
    
    default SocialMediaDTO addLinksToSocialMedia(final SocialMediaDTO socialMediaResource) {
        Link selfLink = linkTo(SocialMediaController.class).slash(socialMediaResource.getIdentity()).withSelfRel();
        socialMediaResource.add(selfLink);
        return socialMediaResource;
    }

    default Iterable<SocialMediaDTO> addLinksToSocialMedia(final Iterable<SocialMediaDTO> socialMediaResources) {
        for (SocialMediaDTO socialMediaResource : socialMediaResources) {
            addLinksToSocialMedia(socialMediaResource);
        }
        return socialMediaResources;
    }
    
    default Page<SocialMediaDTO> addLinksToSocialMedia(final Page<SocialMediaDTO> socialMediaPage) {
        for (SocialMediaDTO socialMediaResource : socialMediaPage.getContent()) {
            addLinksToSocialMedia(socialMediaResource);
        }
        return socialMediaPage;
    }
}
