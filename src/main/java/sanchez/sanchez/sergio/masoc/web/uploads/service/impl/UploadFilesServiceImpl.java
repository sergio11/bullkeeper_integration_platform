package sanchez.sanchez.sergio.masoc.web.uploads.service.impl;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.masoc.mapper.ImageUploadMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.ParentRepository;
import sanchez.sanchez.sergio.masoc.persistence.repository.ScheduledBlockRepository;
import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.masoc.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.masoc.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.masoc.web.uploads.models.UploadFileInfo;
import sanchez.sanchez.sergio.masoc.web.uploads.service.IUploadFilesService;
import sanchez.sanchez.sergio.masoc.web.uploads.strategy.IUploadStrategy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;

/**
 * Upload Files Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class UploadFilesServiceImpl implements IUploadFilesService {

    private static Logger logger = LoggerFactory.getLogger(UploadFilesServiceImpl.class);

    private final IUploadStrategy<String, RequestUploadFile> uploadStrategy;
    private final ParentRepository parentRepository;
    private final SonRepository sonRepository;
    private final ImageUploadMapper imageUploadMapper;
    private final ScheduledBlockRepository scheduledRepository;

    /**
     * 
     * @param uploadStrategy
     * @param parentRepository
     * @param imageUploadMapper
     * @param sonRepository
     * @param scheduledRepository
     */
    public UploadFilesServiceImpl(IUploadStrategy<String, RequestUploadFile> uploadStrategy, 
            ParentRepository parentRepository, ImageUploadMapper imageUploadMapper,
            SonRepository sonRepository, final ScheduledBlockRepository scheduledRepository) {
        this.uploadStrategy = uploadStrategy;
        this.parentRepository = parentRepository;
        this.imageUploadMapper = imageUploadMapper;
        this.sonRepository = sonRepository;
        this.scheduledRepository = scheduledRepository;
    }
    
    
    /**
     * Upload Parent Profile Image
     */
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
    
    /**
     * Upload Son Profile Image
     */
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
    
    /**
     * Get Profile Image
     */
    @Override
    public UploadFileInfo getImage(String id) {
    	return uploadStrategy.get(id);
    }
    
    /**
     * Delete Profile Image
     */
    @Override
    public void deleteImage(String id) {
        uploadStrategy.delete(id);
    }
    
    /**
     * Upload Parent Profile Image From URL
     */
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
    
    /**
     * Upload Scheduled Block Image
     */
    @Override
	public ImageDTO uploadScheduledBlockImage(final ObjectId childId, final ObjectId blockId,
			final RequestUploadFile requestUploadFile) {
    	Assert.notNull(childId, "Child Id can not be null");
        Assert.notNull(blockId, "Block Id can not be null");
        Assert.notNull(requestUploadFile, "Request Upload File can not be null");
        
        // Get Scheduled Block
        final ScheduledBlockEntity scheduledBlockEntity = 
        		scheduledRepository.findByIdAndSonId(blockId, childId);
        
        // Save File
        final String scheduledBlockImage = uploadStrategy.save(requestUploadFile);
        
       
        if(scheduledBlockEntity.getImage() != null)
             uploadStrategy.delete(scheduledBlockEntity.getImage());
        
        scheduledBlockEntity.setImage(scheduledBlockImage);
        
        scheduledRepository.save(scheduledBlockEntity);
        
        // Get Upload file info
        UploadFileInfo fileInfo = uploadStrategy.get(scheduledBlockImage);
        
        return imageUploadMapper.uploadFileInfoToImageDTO(fileInfo);
     
	}
    
    /**
     * Get Scheduled Block Image
     */
    @Override
	public UploadFileInfo getScheduledBlockImage(final ObjectId childId, final ObjectId blockId) {
    	Assert.notNull(childId, "Child Id can not be null");
        Assert.notNull(blockId, "Block Id can not be null");
        
        // Get Scheduled Block
        final ScheduledBlockEntity scheduledBlockEntity = 
        		scheduledRepository.findByIdAndSonId(blockId, childId);
         
        // Get Upload file info
        return uploadStrategy.get(scheduledBlockEntity.getImage());
       
	}

    @PostConstruct
    protected void init() {
        Assert.notNull(uploadStrategy, "Upload Strategy can not be null");
    }
}
