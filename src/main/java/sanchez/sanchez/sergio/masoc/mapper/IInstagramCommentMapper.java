package sanchez.sanchez.sergio.masoc.mapper;


import java.util.List;
import org.jinstagram.entity.comments.CommentData;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.masoc.persistence.entity.CommentEntity;

/**
 * @author sergio
 */
@Mapper
public interface IInstagramCommentMapper {
    
    @Mappings({ 
    	@Mapping(target = "id", ignore=true),
    	@Mapping(source = "instagramComment.id", target="externalId"),
        @Mapping(source = "instagramComment.text", target = "message"),
        @Mapping(expression="java(new java.util.Date(Long.parseLong(instagramComment.getCreatedTime())))", target = "createdTime"),
        @Mapping(expression="java(sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum.INSTAGRAM)", target = "socialMedia"),
        @Mapping(source = "instagramComment.commentFrom.fullName", target = "author.name"),
        @Mapping(source = "instagramComment.commentFrom.id", target = "author.externalId"),
        @Mapping(source = "instagramComment.commentFrom.profilePicture", target = "author.image")
        
    })
    @Named("instagramCommentToCommentEntity")
    CommentEntity instagramCommentToCommentEntity(CommentData instagramComment); 
	
    @IterableMapping(qualifiedByName = "instagramCommentToCommentEntity")
    List<CommentEntity> instagramCommentsToCommentEntities(List<CommentData> instagramComments);
}
