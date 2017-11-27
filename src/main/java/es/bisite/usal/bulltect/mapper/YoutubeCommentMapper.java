package es.bisite.usal.bulltect.mapper;

import com.google.api.client.util.ArrayMap;
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
public abstract class YoutubeCommentMapper {
	
	
	protected String parseAuthorChannelId(Comment youtubeComment){
		
		String authorId = "";
		if(youtubeComment.getSnippet() != null) {
			ArrayMap arrayMap = (ArrayMap)youtubeComment.getSnippet().getAuthorChannelId();
			if(arrayMap.containsKey("value"))
				authorId = (String)arrayMap.get("value");
			
		}
		return authorId;
	}
	
    
    @Mappings({ 
    	@Mapping(target = "id", ignore=true),
    	@Mapping(source = "youtubeComment.id", target="externalId"),
        @Mapping(source = "youtubeComment.snippet.likeCount", target = "likes"),
        @Mapping(source = "youtubeComment.snippet.textDisplay", target = "message"),
        @Mapping(expression="java(new java.util.Date(youtubeComment.getSnippet().getPublishedAt().getValue()))", target = "createdTime"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum.YOUTUBE)", target = "socialMedia"),
        @Mapping(expression="java(youtubeComment.getSnippet().getAuthorDisplayName())", target = "author.name"),
        @Mapping(expression="java(parseAuthorChannelId(youtubeComment))", target = "author.externalId"),
        @Mapping(expression="java(youtubeComment.getSnippet().getAuthorProfileImageUrl() + \"?sz=500\")", target="author.image")
        
    })
    @Named("youtubeCommentToCommentEntity")
    public abstract CommentEntity youtubeCommentToCommentEntity(Comment youtubeComment); 
	
    @IterableMapping(qualifiedByName = "youtubeCommentToCommentEntity")
    public abstract List<CommentEntity> youtubeCommentsToCommentEntities(List<Comment> youtubeComments);
}
