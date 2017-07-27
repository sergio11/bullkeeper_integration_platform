package sanchez.sanchez.sergio.mapper;

import com.google.api.services.youtube.model.Comment;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;

/**
 * @author sergio
 */
@Mapper
public interface IYoutubeCommentMapper {
    
    @Mappings({ 
        @Mapping(target = "id", ignore=true),
        @Mapping(source = "youtubeComment.snippet.likeCount", target = "likes"),
        @Mapping(source = "youtubeComment.snippet.textDisplay", target = "message"),
        @Mapping(expression="java(new java.util.Date(youtubeComment.getSnippet().getPublishedAt().getValue()))", target = "createdTime")
    })
    @Named("youtubeCommentToCommentEntity")
    CommentEntity youtubeCommentToCommentEntity(Comment youtubeComment); 
	
    @IterableMapping(qualifiedByName = "youtubeCommentToCommentEntity")
    List<CommentEntity> youtubeCommentsToCommentEntities(List<Comment> youtubeComments);
}
