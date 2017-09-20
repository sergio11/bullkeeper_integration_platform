package es.bisite.usal.bulltect.mapper;


import java.util.List;
import org.jinstagram.entity.comments.CommentData;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;

/**
 * @author sergio
 */
@Mapper
public interface IInstagramCommentMapper {
    
    @Mappings({ 
        @Mapping(target = "id", ignore=true),
        @Mapping(source = "instagramComment.text", target = "message"),
        @Mapping(expression="java(new java.util.Date(Long.parseLong(instagramComment.getCreatedTime())))", target = "createdTime"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum.INSTAGRAM)", target = "socialMedia")
    })
    @Named("instagramCommentToCommentEntity")
    CommentEntity instagramCommentToCommentEntity(CommentData instagramComment); 
	
    @IterableMapping(qualifiedByName = "instagramCommentToCommentEntity")
    List<CommentEntity> instagramCommentsToCommentEntities(List<CommentData> instagramComments);
}
