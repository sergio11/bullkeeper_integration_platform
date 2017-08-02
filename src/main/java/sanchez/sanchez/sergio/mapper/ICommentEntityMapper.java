package sanchez.sanchez.sergio.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.dto.CommentDTO;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;

/**
 * @author sergio
 */
@Mapper
public interface ICommentEntityMapper {
    
    @Mappings({
        @Mapping(source = "commentEntity.userEntity.fullName", target = "user"),
        @Mapping(source = "commentEntity.createdTime", target = "createdTime", dateFormat = "dd/MM/yyyy")
    })
    @Named("commentEntityToCommentDTO")
    CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity); 
	
    @IterableMapping(qualifiedByName = "commentEntityToCommentDTO")
    List<CommentDTO> commentEntitiesToCommentDTOs(List<CommentEntity> commentEntities);
}