package es.bisite.usal.bulltect.mapper;

import java.util.List;
import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.web.dto.response.CommentDTO;

/**
 * @author sergio
 */
@Mapper
public abstract class CommentEntityMapper {
	
	@Autowired
	protected PrettyTime prettyTime;
    
    @Mappings({
        @Mapping(expression="java(commentEntity.getId().toString())", target = "identity" ),
        @Mapping(source = "commentEntity.author.name", target = "authorName"),
        @Mapping(source = "commentEntity.author.image", target = "authorPhoto"),
        @Mapping(source = "commentEntity.createdTime", target = "createdTime", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(source = "commentEntity.extractedAt", target = "extractedAt", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(expression="java(prettyTime.format(commentEntity.getExtractedAt()))", target = "extractedAtSince"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.SentimentLevelEnum.fromResult(commentEntity.getAnalysisResults().getSentiment().getResult()).name())",
        target = "sentiment"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.BullyingLevelEnum.fromResult(commentEntity.getAnalysisResults().getBullying().getResult()).name())",
        target = "bullying"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.DrugsLevelEnum.fromResult(commentEntity.getAnalysisResults().getDrugs().getResult()).name())",
        target = "drugs"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.ViolenceLevelEnum.fromResult(commentEntity.getAnalysisResults().getViolence().getResult()).name())",
        target = "violence"),
        @Mapping(expression="java(es.bisite.usal.bulltect.persistence.entity.AdultLevelEnum.fromResult(commentEntity.getAnalysisResults().getAdult().getResult()).name())",
        target = "adult")
    })
    @Named("commentEntityToCommentDTO")
    public abstract CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity); 
	
    @IterableMapping(qualifiedByName = "commentEntityToCommentDTO")
    public abstract List<CommentDTO> commentEntitiesToCommentDTOs(List<CommentEntity> commentEntities);
    
    @IterableMapping(qualifiedByName = "commentEntityToCommentDTO")
    public abstract Set<CommentDTO> commentEntitiesToCommentDTOs(Set<CommentEntity> commentEntities);
    
}
