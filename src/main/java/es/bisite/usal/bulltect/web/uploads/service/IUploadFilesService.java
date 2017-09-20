package es.bisite.usal.bulltect.web.uploads.service;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;

public interface IUploadFilesService {
	ImageDTO uploadParentProfileImage(ObjectId userId, RequestUploadFile requestUploadFile);
}
