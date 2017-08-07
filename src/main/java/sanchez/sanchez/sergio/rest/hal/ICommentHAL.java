package sanchez.sanchez.sergio.rest.hal;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import sanchez.sanchez.sergio.dto.response.CommentDTO;
import sanchez.sanchez.sergio.rest.controller.CommentsController;
/**
 *
 * @author sergio
 */
public interface ICommentHAL {
    
    default CommentDTO addLinksToComment(final CommentDTO commentResource) {
        Link selfLink = linkTo(CommentsController.class).slash(commentResource.getIdentity()).withSelfRel();
        commentResource.add(selfLink);
        return commentResource;
    }

    default List<CommentDTO> addLinksToComments(final List<CommentDTO> commentsResources) {
        for (CommentDTO commentResurce : commentsResources) {
            addLinksToComment(commentResurce);
        }
        return commentsResources;
    }
    
    default Page<CommentDTO> addLinksToComments(final Page<CommentDTO> commentsPage) {
        for (CommentDTO commentResource : commentsPage.getContent()) {
            addLinksToComment(commentResource);
        }
        return commentsPage;
    }
}
