package sanchez.sanchez.sergio.rest.hal;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import sanchez.sanchez.sergio.dto.SocialMediaDTO;
import sanchez.sanchez.sergio.rest.controller.SocialMediaController;
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

    default List<SocialMediaDTO> addLinksToSocialMedia(final List<SocialMediaDTO> socialMediaResources) {
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
