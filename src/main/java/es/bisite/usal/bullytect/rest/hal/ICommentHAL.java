package es.bisite.usal.bullytect.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;

import es.bisite.usal.bullytect.dto.response.CommentDTO;
import es.bisite.usal.bullytect.rest.controller.CommentsController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
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

    default Iterable<CommentDTO> addLinksToComments(final Iterable<CommentDTO> commentsResources) {
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
