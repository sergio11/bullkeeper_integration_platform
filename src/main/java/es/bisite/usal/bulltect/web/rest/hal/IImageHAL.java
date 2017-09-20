package es.bisite.usal.bulltect.web.rest.hal;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.rest.controller.ImagesController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author sergio
 */
public interface IImageHAL {
    
    default ImageDTO addLinksToImage(final ImageDTO imageResource) {
        try {
            //Download Image Link
            ResponseEntity<byte[]> downloadImageLinkBuilder = methodOn(ImagesController.class)
                    .download(imageResource.getIdentity());
            Link downloadImageLink = linkTo(downloadImageLinkBuilder).withRel("download_image");
            imageResource.add(downloadImageLink);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
        return imageResource;
    }

    default Iterable<ImageDTO> addLinksToImages(final Iterable<ImageDTO> imageResources) {
        for (ImageDTO imageResource : imageResources) {
            addLinksToImage(imageResource);
        }
        return imageResources;
    }
}
