package sanchez.sanchez.sergio.mapper;

import com.restfb.types.Comment;
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
public interface IFacebookCommentMapper {
    
    @Mappings({ 
        @Mapping(target = "id", ignore=true),
        @Mapping(source = "facebookComment.likeCount", target = "likes"),
        @Mapping(expression="java(sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum.FACEBOOK)", target = "socialMedia")
    })
    @Named("facebookCommentToCommentEntity")
    CommentEntity facebookCommentToCommentEntity(Comment facebookComment); 
	
    @IterableMapping(qualifiedByName = "facebookCommentToCommentEntity")
    List<CommentEntity> facebookCommentsToCommentEntities(List<Comment> facebookComments);
       
}
