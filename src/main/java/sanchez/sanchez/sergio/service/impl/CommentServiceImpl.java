package sanchez.sanchez.sergio.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.service.ICommentsService;

/**
 *
 * @author sergio
 */
@Service("commentService")
public class CommentServiceImpl implements ICommentsService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComments(List<CommentEntity> comments) {
       commentRepository.save(comments);
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(commentRepository, "CommentRepository cannot be null");
    }
    
}
