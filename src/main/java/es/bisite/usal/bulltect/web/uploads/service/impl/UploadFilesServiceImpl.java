package es.bisite.usal.bulltect.web.uploads.service.impl;

import es.bisite.usal.bulltect.mapper.ImageUploadMapper;
import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.persistence.repository.ParentRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;
import es.bisite.usal.bulltect.web.uploads.service.IUploadFilesService;
import es.bisite.usal.bulltect.web.uploads.strategy.IUploadStrategy;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;

@Service
public final class UploadFilesServiceImpl implements IUploadFilesService {

    private static Logger logger = LoggerFactory.getLogger(UploadFilesServiceImpl.class);

    private final IUploadStrategy<String, RequestUploadFile> uploadStrategy;
    private final ParentRepository parentRepository;
    private final SonRepository sonRepository;
    private final ImageUploadMapper imageUploadMapper;

    public UploadFilesServiceImpl(IUploadStrategy<String, RequestUploadFile> uploadStrategy, 
            ParentRepository parentRepository, ImageUploadMapper imageUploadMapper, SonRepository sonRepository) {
        this.uploadStrategy = uploadStrategy;
        this.parentRepository = parentRepository;
        this.imageUploadMapper = imageUploadMapper;
        this.sonRepository = sonRepository;
    }
    
    @Override
    public ImageDTO uploadParentProfileImage(ObjectId userId, RequestUploadFile requestUploadFile) {
        Assert.notNull(userId, "User Id can not be null");
        Assert.notNull(requestUploadFile, "Request Upload File can not be null");
        final ParentEntity parentEntity = parentRepository.findOne(userId);
        final String profileImageId = uploadStrategy.save(requestUploadFile);
        if(parentEntity.getProfileImage() != null)
             uploadStrategy.delete(parentEntity.getProfileImage());
        parentEntity.setProfileImage(profileImageId);
        parentRepository.save(parentEntity);
        UploadFileInfo fileInfo = uploadStrategy.get(profileImageId);
        return imageUploadMapper.uploadFileInfoToImageDTO(fileInfo);
    }
    
    @Override
    public ImageDTO uploadSonProfileImage(ObjectId userId, RequestUploadFile requestUploadFile) {
        Assert.notNull(userId, "User Id can not be null");
        Assert.notNull(requestUploadFile, "Request Upload File can not be null");
        final SonEntity sonEntity = sonRepository.findOne(userId);
        final String profileImageId = uploadStrategy.save(requestUploadFile);
        if(sonEntity.getProfileImage() != null)
            uploadStrategy.delete(sonEntity.getProfileImage());
        sonEntity.setProfileImage(profileImageId);
        sonRepository.save(sonEntity);
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
    
    @Override
    public ImageDTO uploadParentProfileImageFromUrl(ObjectId userId, String imageUrl) {
        Assert.notNull(userId, "User Id can not be null");
        Assert.notNull(imageUrl, "Image URL can not be null");
        Assert.hasLength(imageUrl, "Image URL can not be empty");
        try {
            URL url = new URL(imageUrl);
            byte[] content = IOUtils.toByteArray(url.openStream());
            RequestUploadFile requestUpload  = new RequestUploadFile(content, 
                    MediaType.IMAGE_PNG_VALUE, "facebook_profile_image_for_" + userId.toString());
            return uploadParentProfileImage(userId, requestUpload);
        } catch (MalformedURLException ex) {
            logger.debug(imageUrl);
            logger.error("MalformedURLException for user " + userId.toString());
        } catch (IOException ex) {
            logger.error("IOException");
        }
        return null;
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(uploadStrategy, "Upload Strategy can not be null");
    }

    
}
