package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.domain.service.ICommentsService;
import sanchez.sanchez.sergio.bullkeeper.mapper.CommentEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SentimentLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentDTO;

/**
 * @author sergio
 */
@Service("commentsService")
public class CommentsServiceImpl implements ICommentsService {
    
    private final CommentRepository commentRepository;
    private final CommentEntityMapper commentEntityMapper;

    /**
     * 
     * @param commentRepository
     * @param commentEntityMapper
     */
    public CommentsServiceImpl(CommentRepository commentRepository, CommentEntityMapper commentEntityMapper) {
        this.commentRepository = commentRepository;
        this.commentEntityMapper = commentEntityMapper;
    }
    
    /**
     * 
     */
    @Override
    public Long getTotalComments() {
        return commentRepository.count();
    }

    /**
     * 
     */
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
    
    
    /**
     * 
     */
    @Override
    public CommentDTO getCommentById(String id) {
        CommentEntity commentEntity = commentRepository.findOne(new ObjectId(id));
        return commentEntityMapper.commentEntityToCommentDTO(commentEntity);
    }
    
    /**
     * 
     */
    @Override
    public Page<CommentDTO> findPaginated(Pageable pageable) {
        Page<CommentEntity> commentsPage = commentRepository.findAll(pageable);
        return commentsPage.map(new Converter<CommentEntity, CommentDTO>(){
            @Override
            public CommentDTO convert(CommentEntity s) {
               return commentEntityMapper.commentEntityToCommentDTO(s);
            }
        });
    }

    /**
     * 
     */
    @Override
    public Page<CommentDTO> getCommentByKidIdPaginated(Pageable pageable, String userId) {
        Page<CommentEntity> commentsPage =  commentRepository.findAllByKidIdOrderByExtractedAtDesc(new ObjectId(userId), pageable);
        return commentsPage.map(new Converter<CommentEntity, CommentDTO>(){
            @Override
            public CommentDTO convert(CommentEntity s) {
               return commentEntityMapper.commentEntityToCommentDTO(s);
            }
        });
    }
    
    /**
     * 
     */
    @Override
	public Iterable<CommentDTO> getCommentByKidId(String userId) {
		final List<CommentEntity> commentEntities = commentRepository.findAllByKidIdOrderByExtractedAtDesc(new ObjectId(userId));
		return commentEntityMapper.commentEntitiesToCommentDTOs(commentEntities);
	}
    
    /**
     * 
     */
    @Override
	public Iterable<CommentDTO> getComments(String idSon, String author, Date from, SocialMediaTypeEnum[] socialMedias,
			ViolenceLevelEnum violence, DrugsLevelEnum drugs, BullyingLevelEnum bullying, AdultLevelEnum adult,
			final SentimentLevelEnum sentiment) {
    	
    	Assert.notNull(idSon, "Id son can not be null");
		Assert.isTrue(ObjectId.isValid(idSon), "Id Son should have a valid format");
		Assert.isTrue(from.before(new Date()), "From must be a date before the current one");
    	
    	final List<CommentEntity> commentEntities = commentRepository.getComments(new ObjectId(idSon), author, from, 
    			socialMedias, violence, drugs, bullying, adult, sentiment);
    	return commentEntityMapper.commentEntitiesToCommentDTOs(commentEntities);
	}
    
    /**
     * 
     */
    @Override
	public Iterable<CommentDTO> getComments(List<String> identities, String author, Date from,
			final SocialMediaTypeEnum[] socialMedias, final ViolenceLevelEnum violence, final DrugsLevelEnum drugs,
			final BullyingLevelEnum bullying, final AdultLevelEnum adult, final SentimentLevelEnum sentiment) {
    	
    	Assert.notNull(identities, "identities can not be null");
    	Assert.isTrue(from.before(new Date()), "From must be a date before the current one");
    	
    	final List<CommentEntity> commentEntities = commentRepository.getComments(identities.stream().
    			filter((indentity) -> ObjectId.isValid(indentity)).map((indentity) -> new ObjectId(indentity)).collect(Collectors.toList()), author, from, 
    			socialMedias, violence, drugs, bullying, adult, sentiment);
    	return commentEntityMapper.commentEntitiesToCommentDTOs(commentEntities);
	}
    
    /**
     * 
     */
    @PostConstruct
    protected void init(){
        Assert.notNull(commentRepository, "CommentRepository cannot be null");
        Assert.notNull(commentEntityMapper, "ICommentEntityMapper cannot be null");
    }
}
