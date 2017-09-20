package es.bisite.usal.bulltect.web.uploads.strategy.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.web.uploads.exceptions.FileNotFoundException;
import es.bisite.usal.bulltect.web.uploads.exceptions.UploadFailException;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;
import es.bisite.usal.bulltect.web.uploads.strategy.IUploadStrategy;
import org.springframework.context.annotation.ScopedProxyMode;
import es.bisite.usal.bulltect.web.uploads.config.conditions.FileSystemStrategySelected;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Conditional(FileSystemStrategySelected.class)
public class UserImagesFileSystemUploadStrategy implements IUploadStrategy<String, RequestUploadFile> {

    private static Logger logger = LoggerFactory.getLogger(UserImagesFileSystemUploadStrategy.class);

    @Autowired
    private HttpServletRequest request;
    @Value("${uploads.user.images.folder}")
    private String userImagesFolder;
    private String realPathtoUploads;

   
    private File getFileToSave(String contentType) {
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), contentType.replace("image/", ""));
        return new File(realPathtoUploads, name);
    }

    @Override
    public String save(final RequestUploadFile fileInfo) {
        try {
            // get new file to save bytes
            File fileToSave = getFileToSave(fileInfo.getContentType());
            Files.write(fileToSave.toPath(), fileInfo.getBytes(), StandardOpenOption.CREATE);
            // return name
            return fileToSave.getName();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new UploadFailException();
        }
    }

    @Override
    public void delete(String id) {
        if (id != null) {
            File file = new File(realPathtoUploads, id);
            if (file.exists() && file.canWrite()) {
                file.delete();
            }
        }

    }

    @Override
    public UploadFileInfo get(String id) {
        UploadFileInfo info = null;
        if (id != null) {
            try {
                File file = new File(realPathtoUploads, id);
                if (file.exists() && file.canRead()) {
                    String contentType = Files.probeContentType(file.toPath());
                    byte[] content = Files.readAllBytes(file.toPath());
                    info = new UploadFileInfo(file.length(), contentType, content);
                } else {
                    throw new FileNotFoundException();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage());
                throw new UploadFailException();
            }
        }
        return info;
    }
    
    @Override
    public Boolean exists(String id) {
        File file = new File(realPathtoUploads, id);
        return file.exists() && file.canRead();
    }
    
    @PostConstruct
    public void init() {
        logger.debug("init UserImagesFileSystemUploadStrategy ...");
        realPathtoUploads = request.getServletContext().getRealPath(userImagesFolder);
        if (!new File(realPathtoUploads).exists()) {
            new File(realPathtoUploads).mkdirs();
        }
    }
}
