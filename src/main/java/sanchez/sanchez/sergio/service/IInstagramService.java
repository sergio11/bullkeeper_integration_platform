package sanchez.sanchez.sergio.service;

import java.util.List;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IInstagramService {
    
    List<CommentEntity> getComments(String accessToken);
    
}
