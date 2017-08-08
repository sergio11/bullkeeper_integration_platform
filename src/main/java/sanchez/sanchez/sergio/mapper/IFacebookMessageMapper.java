package sanchez.sanchez.sergio.mapper;

import com.restfb.types.Comment;
import com.restfb.types.Message;
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
public interface IFacebookMessageMapper {
    
    @Mappings({ 
        @Mapping(target = "id", ignore=true)
    })
    @Named("facebookMessageToCommentEntity")
    CommentEntity facebookMessageToCommentEntity(Message facebookMessage); 
	
    @IterableMapping(qualifiedByName = "facebookMessageToCommentEntity")
    List<CommentEntity> facebookMessagesToCommentEntities(List<Message> facebookMessages);
       
}
