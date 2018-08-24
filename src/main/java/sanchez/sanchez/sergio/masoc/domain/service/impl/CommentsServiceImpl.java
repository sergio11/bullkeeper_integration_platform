package sanchez.sanchez.sergio.masoc.domain.service.impl;


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

import sanchez.sanchez.sergio.masoc.domain.service.ICommentsService;
import sanchez.sanchez.sergio.masoc.mapper.CommentEntityMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.masoc.web.dto.response.CommentDTO;

/**
 * @author sergio
 */
@Service("commentsService")
public class CommentsServiceImpl implements ICommentsService {
    
    private final CommentRepository commentRepository;
    private final CommentEntityMapper commentEntityMapper;

    public CommentsServiceImpl(CommentRepository commentRepository, CommentEntityMapper commentEntityMapper) {
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
    
    @Override
    public CommentDTO getCommentById(String id) {
        CommentEntity commentEntity = commentRepository.findOne(new ObjectId(id));
        return commentEntityMapper.commentEntityToCommentDTO(commentEntity);
    }

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

    @Override
    public Page<CommentDTO> getCommentBySonIdPaginated(Pageable pageable, String userId) {
        Page<CommentEntity> commentsPage =  commentRepository.findAllBySonEntityIdOrderByExtractedAtDesc(new ObjectId(userId), pageable);
        return commentsPage.map(new Converter<CommentEntity, CommentDTO>(){
            @Override
            public CommentDTO convert(CommentEntity s) {
               return commentEntityMapper.commentEntityToCommentDTO(s);
            }
        });
    }
    
    @Override
	public Iterable<CommentDTO> getCommentBySonId(String userId) {
		final List<CommentEntity> commentEntities = commentRepository.findAllBySonEntityIdOrderByExtractedAtDesc(new ObjectId(userId));
		return commentEntityMapper.commentEntitiesToCommentDTOs(commentEntities);
	}
    
    @Override
	public Iterable<CommentDTO> getComments(String idSon, String author, Date from, SocialMediaTypeEnum[] socialMedias,
			ViolenceLevelEnum violence, DrugsLevelEnum drugs, BullyingLevelEnum bullying, AdultLevelEnum adult) {
    	
    	Assert.notNull(idSon, "Id son can not be null");
		Assert.isTrue(ObjectId.isValid(idSon), "Id Son should have a valid format");
		Assert.isTrue(from.before(new Date()), "From must be a date before the current one");
    	
    	final List<CommentEntity> commentEntities = commentRepository.getComments(new ObjectId(idSon), author, from, socialMedias, violence, drugs, bullying, adult);
    	return commentEntityMapper.commentEntitiesToCommentDTOs(commentEntities);
	}
    
    @Override
	public Iterable<CommentDTO> getComments(List<String> identities, String author, Date from,
			SocialMediaTypeEnum[] socialMedias, ViolenceLevelEnum violence, DrugsLevelEnum drugs,
			BullyingLevelEnum bullying, AdultLevelEnum adult) {
    	
    	Assert.notNull(identities, "identities can not be null");
    	Assert.isTrue(from.before(new Date()), "From must be a date before the current one");
    	
    	final List<CommentEntity> commentEntities = commentRepository.getComments(identities.stream().
    			filter((indentity) -> ObjectId.isValid(indentity)).map((indentity) -> new ObjectId(indentity)).collect(Collectors.toList()), author, from, socialMedias, violence, drugs, bullying, adult);
    	return commentEntityMapper.commentEntitiesToCommentDTOs(commentEntities);
	}
    
    @PostConstruct
    protected void init(){
        Assert.notNull(commentRepository, "CommentRepository cannot be null");
        Assert.notNull(commentEntityMapper, "ICommentEntityMapper cannot be null");
    }
}
