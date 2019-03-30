package sanchez.sanchez.sergio.bullkeeper.web.uploads.strategy.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.exceptions.UploadFailException;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.strategy.IUploadStrategy;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Component
@ConditionalOnProperty(name = "upload.strategy", havingValue = "gridfs", matchIfMissing = false)
public class UserImagesGridFSUploadStrategy implements IUploadStrategy<String, RequestUploadFile> {

    private static Logger logger = LoggerFactory.getLogger(UserImagesGridFSUploadStrategy.class);

    @Autowired
    private GridFsOperations gridOperations;

    /**
     * Save
     */
    @Override
    public String save(final RequestUploadFile fileinfo) throws UploadFailException {
        Assert.notNull(fileinfo, "Request Upload File can not be null");
        // Define metaData
        DBObject metaData = new BasicDBObject();
        metaData.put(RequestUploadFile.ORIGINAL_NAME, fileinfo.getOriginalName());
        metaData.put(RequestUploadFile.CONTENT_TYPE, fileinfo.getContentType());
        // Create InputStream from ByteArray.
        InputStream imageStream = new ByteArrayInputStream(fileinfo.getBytes());
        // Store file in GridFS
        GridFSFile gridFile = gridOperations.store(imageStream, fileinfo.getOriginalName(), fileinfo.getContentType(), metaData);
        logger.debug("Grid FS File id -> " + gridFile.getId().toString());
        return gridFile.getId().toString();

    }

    /**
     * Delete
     */
    @Override
    public void delete(String id) {
        Assert.hasLength(id, "Id can not be empty");
        // delete image via id
        gridOperations.delete(new Query(Criteria.where("_id").is(id)));
    }

    /**
     * Get
     */
    @Override
    public UploadFileInfo get(String id) throws UploadFailException {
        Assert.hasLength(id, "Id can not be empty");
        try {
            // read file from MongoDB
            GridFSDBFile imageFile = gridOperations.findOne(new Query(Criteria.where("_id").is(id)));
            byte[] content = IOUtils.toByteArray(imageFile.getInputStream());
            return new UploadFileInfo(id, imageFile.getLength(), imageFile.getContentType(), content);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new UploadFailException();
        }

    }
    
    /**
     * Exists
     */
    @Override
    public Boolean exists(String id) {
        Assert.hasLength(id, "Id can not be empty");
        GridFSDBFile imageFile = gridOperations.findOne(new Query(Criteria.where("_id").is(id)));
        return imageFile != null ? Boolean.TRUE : Boolean.FALSE;
    }

    @PostConstruct
    protected void init() {
        logger.debug("init UserImagesGridFSUploadStrategy ...");
        Assert.notNull(gridOperations, "GridOperations can not be null");
    }
}
