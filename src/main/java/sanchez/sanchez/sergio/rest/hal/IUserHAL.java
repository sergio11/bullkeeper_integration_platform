package sanchez.sanchez.sergio.rest.hal;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import sanchez.sanchez.sergio.admin.controller.UsersController;
import sanchez.sanchez.sergio.dto.UserDTO;
/**
 *
 * @author sergio
 */
public interface IUserHAL {
    
    default UserDTO addLinksToUser(final UserDTO userResource) {
        Link selfLink = linkTo(UsersController.class).slash(userResource.getIdentity()).withSelfRel();
        userResource.add(selfLink);
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
