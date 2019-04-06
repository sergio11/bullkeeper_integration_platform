package sanchez.sanchez.sergio.bullkeeper.web.uploads.service.impl;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.mapper.ImageUploadMapper;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.strategy.IUploadStrategy;


/**
 * Upload Files Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class UploadFilesServiceImpl implements IUploadFilesService {

    private static Logger logger = LoggerFactory.getLogger(UploadFilesServiceImpl.class);

    private final IUploadStrategy<String, RequestUploadFile> uploadStrategy;
    private final ImageUploadMapper imageUploadMapper;

    /**
     * 
     * @param uploadStrategy
     * @param imageUploadMapper
     */
    public UploadFilesServiceImpl(IUploadStrategy<String, RequestUploadFile> uploadStrategy, ImageUploadMapper imageUploadMapper) {
        this.uploadStrategy = uploadStrategy;
        this.imageUploadMapper = imageUploadMapper;
    }
    
    /**
     * Save
     * @param requestUploadFile
     */
    @Override
	public String save(final RequestUploadFile requestUploadFile) {
    	Assert.notNull(requestUploadFile, "Request Upload File can not be null");
    	return uploadStrategy.save(requestUploadFile);
	}


    /**
     * Get Image
     * @param id
     */
	@Override
	public ImageDTO getImage(final String id) {
		Assert.notNull(id, "Id can not be null");
		final UploadFileInfo uploadFileInfo = uploadStrategy.get(id);
		return imageUploadMapper.uploadFileInfoToImageDTO(uploadFileInfo);
	}

	/**
	 * Get File Info
	 * @param id
	 */
	@Override
	public UploadFileInfo getFileInfo(final String id) {
		Assert.notNull(id, "Id can not be null");
		return uploadStrategy.get(id);
	}

	/**
	 * Delete 
	 * @param id
	 */
	@Override
	public void delete(final String id) {
		Assert.notNull(id, "Id can not be null");
		uploadStrategy.delete(id);
		
	}
    
	/**
	 * 
	 */
    @PostConstruct
    protected void init() {
        Assert.notNull(uploadStrategy, "Upload Strategy can not be null");
        Assert.notNull(imageUploadMapper, "Image Upload Mapper can not be null");
    }
	
}
