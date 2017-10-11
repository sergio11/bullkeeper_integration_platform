package es.bisite.usal.bulltect.web.uploads.service;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;

public interface IUploadFilesService {
    ImageDTO uploadParentProfileImage(ObjectId userId, RequestUploadFile requestUploadFile);
    ImageDTO uploadSonProfileImage(ObjectId userId, RequestUploadFile requestUploadFile);
    ImageDTO uploadParentProfileImageFromUrl(ObjectId userId, String imageUrl);
    UploadFileInfo getProfileImage(String id);
    void deleteProfileImage(String id);
}
