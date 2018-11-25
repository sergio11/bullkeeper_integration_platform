package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.mapper.KidEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TaskRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;

import javax.annotation.PostConstruct;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Service
public class KidServiceImpl implements IKidService {

    private static Logger logger = LoggerFactory.getLogger(KidServiceImpl.class);

    private final KidRepository kidRepository;
    private final KidEntityMapper kidEntityMapper;
    private final AlertRepository alertRepository;
    private final CommentRepository commentRepository;
    private final SocialMediaRepository socialMediaRepository;
    private final TaskRepository taskRepository;
    private final IUploadFilesService uploadFilesService;

    /**
     * 
     * @param kidRepository
     * @param kidEntityMapper
     * @param alertRepository
     * @param commentRepository
     * @param socialMediaRepository
     * @param taskRepository
     * @param uploadFilesService
     */
    public KidServiceImpl(KidRepository kidRepository, 
            KidEntityMapper kidEntityMapper, AlertRepository alertRepository,
            CommentRepository commentRepository, SocialMediaRepository socialMediaRepository, 
            TaskRepository taskRepository, IUploadFilesService uploadFilesService) {
        super();
        this.kidRepository = kidRepository;
        this.kidEntityMapper = kidEntityMapper;
        this.alertRepository = alertRepository;
        this.commentRepository = commentRepository;
        this.socialMediaRepository = socialMediaRepository;
        this.taskRepository = taskRepository;
        this.uploadFilesService = uploadFilesService;
    }

    /**
     * 
     */
    @Override
    public Page<KidDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<KidEntity> childrenPage = kidRepository.findAll(pageable);
        return childrenPage.map(new Converter<KidEntity, KidDTO>() {
            @Override
            public KidDTO convert(KidEntity sonEntity) {
                return kidEntityMapper.kidEntityToKidDTO(sonEntity);
            }
        });
        
    }

    /**
     * 
     */
    @Override
    public Page<KidDTO> findPaginated(Pageable pageable) {
        Page<KidEntity> childrenPage = kidRepository.findAll(pageable);
        return childrenPage.map(new Converter<KidEntity, KidDTO>() {
            @Override
            public KidDTO convert(KidEntity sonEntity) {
                return kidEntityMapper.kidEntityToKidDTO(sonEntity);
            }
        });
    }

    /**
     * 
     */
    @Override
    public KidDTO getKidById(String id) {
        KidEntity sonEntity = kidRepository.findOne(new ObjectId(id));
        return kidEntityMapper.kidEntityToKidDTO(sonEntity);
    }

    /**
     * 
     */
    @Override
    public Long getTotalKids() {
        return kidRepository.count();
    }
    
    /**
     * 
     */
    @Override
    public String getProfileImage(ObjectId id) {
        return kidRepository.getProfileImageIdByUserId(id);
    }
    
    /**
     * 
     */
    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.isTrue(ObjectId.isValid(id), "Id should be a valid Object Id");
        deleteById(new ObjectId(id));
    }
    
    /**
     * 
     */
    @Override
    public void deleteById(ObjectId id) {
    	Assert.notNull(id, "Id can not be null");
    	uploadFilesService.deleteImage(getProfileImage(id));
    	alertRepository.deleteByKidId(id);
        commentRepository.deleteByKid(id);
        socialMediaRepository.deleteByKidId(id);
        taskRepository.deleteByKidId(id);
        kidRepository.delete(id);
    }
    
 
    @PostConstruct
    protected void init() {
        Assert.notNull(kidRepository, "Son Repository can not be null");
        Assert.notNull(kidEntityMapper, "Son Entity Mapper can not be null");
        Assert.notNull(alertRepository, "Alert Repository can not be null");
        Assert.notNull(commentRepository, "Comment Repository can not be null");
        Assert.notNull(socialMediaRepository, "Social Media Repository can not be null");
        Assert.notNull(taskRepository, "Task Repository can not be null");
        Assert.notNull(uploadFilesService, "Upload File Service can not be null");
    }
}
