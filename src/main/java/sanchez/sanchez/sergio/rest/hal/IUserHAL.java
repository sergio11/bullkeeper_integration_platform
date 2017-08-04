package sanchez.sanchez.sergio.rest.hal;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import sanchez.sanchez.sergio.dto.UserDTO;
import sanchez.sanchez.sergio.rest.controller.UsersController;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import sanchez.sanchez.sergio.dto.SocialMediaDTO;

/**
 *
 * @author sergio
 */
public interface IUserHAL {
    
    default UserDTO addLinksToUser(final UserDTO userResource) {
        Link selfLink = linkTo(UsersController.class).slash(userResource.getIdentity()).withSelfRel();
        userResource.add(selfLink);
            try {
                ResponseEntity<APIResponse<PagedResources>> methodLinkBuilder = methodOn(UsersController.class)
                        .getCommentsByUserId(null, null, userResource.getIdentity());
                Link commentsLink = linkTo(methodLinkBuilder).withRel("comments");
                userResource.add(commentsLink);
                
                ResponseEntity<APIResponse<List<SocialMediaDTO>>> socialMediasLinkBuilder = methodOn(UsersController.class)
                        .getSocialMediaByUserId(userResource.getIdentity());
                Link socialMediaLink = linkTo(socialMediasLinkBuilder).withRel("socialMedia");
                userResource.add(socialMediaLink);
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }

        return userResource;
    }

    default List<UserDTO> addLinksToUsers(final List<UserDTO> usersResources) {
        for (UserDTO userResource : usersResources) {
            addLinksToUser(userResource);
        }
        return usersResources;
    }
    
    default Page<UserDTO> addLinksToUsers(final Page<UserDTO> usersPage) {
        for (UserDTO userResource : usersPage.getContent()) {
            addLinksToUser(userResource);
        }
        return usersPage;
    }
}
