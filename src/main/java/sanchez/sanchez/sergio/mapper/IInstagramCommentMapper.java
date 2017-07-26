package sanchez.sanchez.sergio.mapper;

import com.restfb.types.Comment;
import java.util.List;
import org.jinstagram.entity.comments.CommentData;
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
public interface IInstagramCommentMapper {
    
    @Mappings({ 
        @Mapping(target = "id", ignore=true),
        @Mapping(source = "instagramComment.text", target = "message"),
        @Mapping(expression="java(new java.util.Date(instagramComment.getCreatedTime()))", target = "createdTime")
    })
    @Named("instagramCommentToCommentEntity")
    CommentEntity instagramCommentToCommentEntity(CommentData instagramComment); 
	
    @IterableMapping(qualifiedByName = "instagramCommentToCommentEntity")
    List<CommentEntity> instagramCommentsToCommentEntities(List<CommentData> instagramComments);
}
