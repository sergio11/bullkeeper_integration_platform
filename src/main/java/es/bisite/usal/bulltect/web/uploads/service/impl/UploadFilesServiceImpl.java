package es.bisite.usal.bulltect.web.uploads.service.impl;

import es.bisite.usal.bulltect.mapper.ImageUploadMapper;
import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.persistence.repository.ParentRepository;
import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;
import es.bisite.usal.bulltect.web.uploads.service.IUploadFilesService;
import es.bisite.usal.bulltect.web.uploads.strategy.IUploadStrategy;

@Service
public final class UploadFilesServiceImpl implements IUploadFilesService {

    private static Logger logger = LoggerFactory.getLogger(UploadFilesServiceImpl.class);

    private final IUploadStrategy<String, RequestUploadFile> uploadStrategy;
    private final ParentRepository parentRepository;
    private final ImageUploadMapper imageUploadMapper;

    public UploadFilesServiceImpl(IUploadStrategy<String, RequestUploadFile> uploadStrategy, 
            ParentRepository parentRepository, ImageUploadMapper imageUploadMapper) {
        this.uploadStrategy = uploadStrategy;
        this.parentRepository = parentRepository;
        this.imageUploadMapper = imageUploadMapper;
    }

    @Override
    public ImageDTO uploadParentProfileImage(ObjectId userId, RequestUploadFile requestUploadFile) {
        final String profileImageId = uploadStrategy.save(requestUploadFile);
        parentRepository.setProfileImageId(userId, profileImageId);
        UploadFileInfo fileInfo = uploadStrategy.get(profileImageId);
        return imageUploadMapper.uploadFileInfoToImageDTO(fileInfo);
    }
    
    @Override
    public UploadFileInfo getProfileImage(String id) {
    	
    	return uploadStrategy.get(id);
    }
    
    @Override
    public void deleteProfileImage(String id) {
        uploadStrategy.delete(id);
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(uploadStrategy, "Upload Strategy can not be null");
    }

    
}
