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
    	@Mapping(source = "youtubeComment.id", target="externalId"),
        @Mapping(source = "youtubeComment.snippet.likeCount", target = "likes"),
        @Mapping(source = "youtubeComment.snippet.textDisplay", target = "message"),
        @Mapping(expression="java(new java.util.Date(youtubeComment.getSnippet().getPublishedAt().getValue()))", target = "createdTime"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum.YOUTUBE)", target = "socialMedia"),
        @Mapping(expression="java(youtubeComment.getSnippet().getAuthorDisplayName())", target = "author.name"),
        @Mapping(expression="java((String)((com.google.api.client.util.ArrayMap)youtubeComment.getSnippet().getAuthorChannelId()).get(\"value\"))", target = "author.externalId"),
        @Mapping(expression="java(youtubeComment.getSnippet().getAuthorProfileImageUrl())", target="author.image")
        
    })
    @Named("youtubeCommentToCommentEntity")
    CommentEntity youtubeCommentToCommentEntity(Comment youtubeComment); 
	
    @IterableMapping(qualifiedByName = "youtubeCommentToCommentEntity")
    List<CommentEntity> youtubeCommentsToCommentEntities(List<Comment> youtubeComments);
}
