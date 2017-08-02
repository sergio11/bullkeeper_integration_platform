package sanchez.sanchez.sergio.service.impl;

import javax.annotation.PostConstruct;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.dto.CommentDTO;
import sanchez.sanchez.sergio.mapper.ICommentEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.service.ICommentsService;

/**
 *
 * @author sergio
 */
@Service("commentsService")
public class CommentsServiceImpl implements ICommentsService {
    
    private final CommentRepository commentRepository;
    private final ICommentEntityMapper commentEntityMapper;

    public CommentsServiceImpl(CommentRepository commentRepository, ICommentEntityMapper commentEntityMapper) {
        this.commentRepository = commentRepository;
        this.commentEntityMapper = commentEntityMapper;
    }
    
    @Override
    public Long getTotalComments() {
        return commentRepository.count();
    }

    @Override
    public Page<CommentDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size); //get 5 profiles on a page
        Page<CommentEntity> commentsPage = commentRepository.findAll(pageable);
        return commentsPage.map(new Converter<CommentEntity, CommentDTO>(){
            @Override
            public CommentDTO convert(CommentEntity s) {
               return commentEntityMapper.commentEntityToCommentDTO(s);
            }
        });
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(commentRepository, "CommentRepository cannot be null");
        Assert.notNull(commentEntityMapper, "ICommentEntityMapper cannot be null");
    }
    
}
