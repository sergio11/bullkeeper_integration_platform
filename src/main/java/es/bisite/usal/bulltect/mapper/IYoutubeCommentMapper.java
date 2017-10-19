package es.bisite.usal.bulltect.mapper;

import com.google.api.services.youtube.model.Comment;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * @author sergio
 */
@Mapper
public interface IYoutubeCommentMapper {
    
    @Mappings({ 
        @Mapping(target = "id", ignore=true),
        @Mapping(source = "youtubeComment.snippet.likeCount", target = "likes"),
        @Mapping(source = "youtubeComment.snippet.textDisplay", target = "message"),
        @Mapping(expression="java(new java.util.Date(youtubeComment.getSnippet().getPublishedAt().getValue()))", target = "createdTime"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum.YOUTUBE)", target = "socialMedia"),
        @Mapping(expression="java(youtubeComment.getSnippet().getAuthorDisplayName())", target = "from"),
        @Mapping(expression="java(youtubeComment.getSnippet().getAuthorChannelId().toString())", target = "fromId")
    })
    @Named("youtubeCommentToCommentEntity")
    CommentEntity youtubeCommentToCommentEntity(Comment youtubeComment); 
	
    @IterableMapping(qualifiedByName = "youtubeCommentToCommentEntity")
    List<CommentEntity> youtubeCommentsToCommentEntities(List<Comment> youtubeComments);
}
