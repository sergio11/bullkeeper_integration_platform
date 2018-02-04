package es.bisite.usal.bulltect.web.uploads.strategy.impl;


import es.bisite.usal.bulltect.persistence.entity.ImageEntity;
import es.bisite.usal.bulltect.persistence.repository.ImageRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import es.bisite.usal.bulltect.web.uploads.exceptions.FileNotFoundException;
import es.bisite.usal.bulltect.web.uploads.exceptions.UploadFailException;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;
import es.bisite.usal.bulltect.web.uploads.strategy.IUploadStrategy;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@ConditionalOnProperty(name = "upload.strategy", havingValue = "fs", matchIfMissing = true)
public class UserImagesFileSystemUploadStrategy implements IUploadStrategy<String, RequestUploadFile> {

    private static Logger logger = LoggerFactory.getLogger(UserImagesFileSystemUploadStrategy.class);
    
    private final ImageRepository imageRepository;
    private final HttpServletRequest request;
    
    @Value("${uploads.images.folder.name}")
    private String userImagesFolder;
    private String realPathtoUploads;

    public UserImagesFileSystemUploadStrategy(ImageRepository imageRepository, HttpServletRequest request) {
        this.imageRepository = imageRepository;
        this.request = request;
    }
   
   
    private File getFileToSave(String contentType) {
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(50), contentType.replace("image/", ""));
        return new File(realPathtoUploads, name);
    }

    @Override
    public String save(final RequestUploadFile fileInfo) {
        try {
        	
            logger.debug("RequestUploadFile -> " + fileInfo.toString());
            // get new file to save bytes
            File fileToSave = getFileToSave(fileInfo.getContentType());
            Files.write(fileToSave.toPath(), fileInfo.getBytes(), StandardOpenOption.CREATE);
            
            String fileName = FilenameUtils.getBaseName(fileToSave.getName());
            String fileExt = FilenameUtils.getExtension(fileToSave.getName());     
            logger.debug("Filename -> " + fileName + " File Ext -> " + fileExt);
            ImageEntity imageEntitySaved = imageRepository.save(new ImageEntity(fileName, fileExt));
            // return identity
            return imageEntitySaved.getIdentity();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new UploadFailException();
        }
    }

    @Override
    public void delete(String id) {
        Assert.notNull(id, "id can not be null");
        Assert.hasLength(id, "id can not be empty");
        
        ImageEntity imageToDelete = imageRepository.findOne(new ObjectId(id));
        if(imageToDelete == null)
            throw new FileNotFoundException();
        String fileName = imageToDelete.getFileName();
        File file = new File(realPathtoUploads, fileName);
        if (file.exists()) {
            file.delete();
        }
        imageRepository.delete(new ObjectId(id));
        
    }

    @Override
    public UploadFileInfo get(String id) {
        Assert.notNull(id, "id can not be null");
        Assert.hasLength(id, "id can not be empty");
        UploadFileInfo info = null;
        try {
            ImageEntity imageToDelete = imageRepository.findOne(new ObjectId(id));
            if(imageToDelete == null)
                 throw new FileNotFoundException();
            String fileName = imageToDelete.getFileName();
            File file = new File(realPathtoUploads, fileName);
            if (file.exists() && file.canRead()) {
                String contentType = Files.probeContentType(file.toPath());
                byte[] content = Files.readAllBytes(file.toPath());
                info = new UploadFileInfo(id, file.length(), contentType, content);
            } else {
                if(!file.canRead())
                    file.delete();
                imageRepository.delete(new ObjectId(id));
                throw new FileNotFoundException();
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new FileNotFoundException();
        }
        return info;
    }
    
    @Override
    public Boolean exists(String id) {
        Boolean exists = Boolean.FALSE;
        ImageEntity image = imageRepository.findOne(new ObjectId(id));
        if(image != null) {
            String fileName = image.getFileName();
            File file = new File(realPathtoUploads, fileName);
            exists = file.exists() && file.canRead();
        }
        return exists;
    }
    
    @PostConstruct
    public void init() {
        Assert.notNull(userImagesFolder, "User Images Folder can not be null");
        Assert.hasLength(userImagesFolder, "User Images Folder can not be empty");
        realPathtoUploads = request.getServletContext().getRealPath(userImagesFolder);
        if (!new File(realPathtoUploads).exists()) {
            new File(realPathtoUploads).mkdirs();
        }
        logger.debug("init UserImagesFileSystemUploadStrategy on " + realPathtoUploads);
    }
}
