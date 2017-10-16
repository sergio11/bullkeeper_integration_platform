package es.bisite.usal.bulltect.domain.service.impl;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import es.bisite.usal.bulltect.domain.service.ISonService;
import es.bisite.usal.bulltect.mapper.SonEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.repository.AlertRepository;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import es.bisite.usal.bulltect.persistence.repository.SocialMediaRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.persistence.repository.TaskRepository;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import es.bisite.usal.bulltect.web.uploads.service.IUploadFilesService;
import io.jsonwebtoken.lang.Assert;
import javax.annotation.PostConstruct;

@Service
public class SonServiceImpl implements ISonService {

    private static Logger logger = LoggerFactory.getLogger(SonServiceImpl.class);

    private final SonRepository sonRepository;
    private final SonEntityMapper sonEntityMapper;
    private final AlertRepository alertRepository;
    private final CommentRepository commentRepository;
    private final SocialMediaRepository socialMediaRepository;
    private final TaskRepository taskRepository;
    private final IUploadFilesService uploadFilesService;

    public SonServiceImpl(SonRepository sonRepository, 
            SonEntityMapper sonEntityMapper, AlertRepository alertRepository,
            CommentRepository commentRepository, SocialMediaRepository socialMediaRepository, 
            TaskRepository taskRepository, IUploadFilesService uploadFilesService) {
        super();
        this.sonRepository = sonRepository;
        this.sonEntityMapper = sonEntityMapper;
        this.alertRepository = alertRepository;
        this.commentRepository = commentRepository;
        this.socialMediaRepository = socialMediaRepository;
        this.taskRepository = taskRepository;
        this.uploadFilesService = uploadFilesService;
    }

    @Override
    public Page<SonDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<SonEntity> childrenPage = sonRepository.findAll(pageable);
        return childrenPage.map(new Converter<SonEntity, SonDTO>() {
            @Override
            public SonDTO convert(SonEntity sonEntity) {
                return sonEntityMapper.sonEntityToSonDTO(sonEntity);
            }
        });
    }

    @Override
    public Page<SonDTO> findPaginated(Pageable pageable) {
        Page<SonEntity> childrenPage = sonRepository.findAll(pageable);
        return childrenPage.map(new Converter<SonEntity, SonDTO>() {
            @Override
            public SonDTO convert(SonEntity sonEntity) {
                return sonEntityMapper.sonEntityToSonDTO(sonEntity);
            }
        });
    }

    @Override
    public SonDTO getSonById(String id) {
        SonEntity sonEntity = sonRepository.findOne(new ObjectId(id));
        return sonEntityMapper.sonEntityToSonDTO(sonEntity);
    }

    @Override
    public Long getTotalChildren() {
        return sonRepository.count();
    }
    
    @Override
    public String getProfileImage(ObjectId id) {
        return sonRepository.getProfileImageIdByUserId(id);
    }
    
    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.isTrue(ObjectId.isValid(id), "Id should be a valid Object Id");
        alertRepository.deleteBySonId(new ObjectId(id));
        commentRepository.deleteBySonEntity(new ObjectId(id));
        socialMediaRepository.deleteBySonEntityId(new ObjectId(id));
        taskRepository.deleteBySonEntity(new ObjectId(id));
        sonRepository.delete(new ObjectId(id));
        uploadFilesService.deleteProfileImage(getProfileImage(new ObjectId(id)));
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(sonRepository, "Son Repository can not be null");
        Assert.notNull(sonEntityMapper, "Son Entity Mapper can not be null");
        Assert.notNull(alertRepository, "Alert Repository can not be null");
        Assert.notNull(commentRepository, "Comment Repository can not be null");
        Assert.notNull(socialMediaRepository, "Social Media Repository can not be null");
        Assert.notNull(taskRepository, "Task Repository can not be null");
        Assert.notNull(uploadFilesService, "Upload File Service can not be null");
    }
}
