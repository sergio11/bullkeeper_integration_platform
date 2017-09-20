package es.bisite.usal.bulltect.web.uploads.service.impl;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.persistence.repository.ParentRepository;
import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.service.IUploadFilesService;
import es.bisite.usal.bulltect.web.uploads.strategy.IUploadStrategy;

@Service
public final class UploadFilesServiceImpl implements IUploadFilesService {
	
	private static Logger logger = LoggerFactory.getLogger(UploadFilesServiceImpl.class);
	
	private final IUploadStrategy<String, RequestUploadFile> uploadStrategy;
	private final ParentRepository parentRepository;
	
	public UploadFilesServiceImpl(IUploadStrategy<String, RequestUploadFile> uploadStrategy, ParentRepository parentRepository){
		this.uploadStrategy = uploadStrategy;
		this.parentRepository = parentRepository;
	}

	@Override
	public ImageDTO uploadParentProfileImage(ObjectId userId, RequestUploadFile requestUploadFile) {
		final String profileImageId = uploadStrategy.save(requestUploadFile);
		parentRepository.setProfileImageId(userId, profileImageId);
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@PostConstruct
	protected void init(){
		Assert.notNull(uploadStrategy, "Upload Strategy can not be null");
	}
}
