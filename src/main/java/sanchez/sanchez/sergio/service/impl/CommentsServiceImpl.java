package sanchez.sanchez.sergio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.service.ICommentsService;

/**
 *
 * @author sergio
 */
@Service("commentsService")
public class CommentsServiceImpl implements ICommentsService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Long getTotalComments() {
        return commentRepository.count();
    }
    
}
