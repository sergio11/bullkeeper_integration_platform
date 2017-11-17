package es.bisite.usal.bulltect.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bulltect.persistence.entity.AdultLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.BullyingLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.DrugsLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.ViolenceLevelEnum;
import es.bisite.usal.bulltect.web.dto.response.CommentDTO;

/**
 *
 * @author sergio
 */
public interface ICommentsService {
    
    Long getTotalComments();
    CommentDTO getCommentById(String id);
    Page<CommentDTO> findPaginated(Integer page, Integer size);
    Page<CommentDTO> findPaginated(Pageable pageable);
    Page<CommentDTO> getCommentBySonIdPaginated(Pageable pageable, String userId);
    Iterable<CommentDTO> getCommentBySonId(String userId);
    Iterable<CommentDTO> getComments(List<String> identities, String author, Date from,
    		SocialMediaTypeEnum socialMedia, ViolenceLevelEnum violence, DrugsLevelEnum drugs, BullyingLevelEnum bullying,
    		AdultLevelEnum adult);
    Iterable<CommentDTO> getComments(String idSon, String author, Date from,
    		SocialMediaTypeEnum socialMedia, ViolenceLevelEnum violence, DrugsLevelEnum drugs, BullyingLevelEnum bullying,
    		AdultLevelEnum adult);
}
