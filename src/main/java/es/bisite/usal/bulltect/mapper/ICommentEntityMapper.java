package es.bisite.usal.bulltect.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.web.dto.response.CommentDTO;

/**
 * @author sergio
 */
@Mapper
public interface ICommentEntityMapper {
    
    @Mappings({
        @Mapping(expression="java(commentEntity.getId().toString())", target = "identity" ),
        @Mapping(source = "commentEntity.sonEntity.fullName", target = "user"),
        @Mapping(source = "commentEntity.createdTime", target = "createdTime", dateFormat = "yyyy/MM/dd")
    })
    @Named("commentEntityToCommentDTO")
    CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity); 
	
    @IterableMapping(qualifiedByName = "commentEntityToCommentDTO")
    List<CommentDTO> commentEntitiesToCommentDTOs(List<CommentEntity> commentEntities);
    
}
