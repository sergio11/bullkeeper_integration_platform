package sanchez.sanchez.sergio.masoc.web.uploads.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.masoc.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.masoc.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.masoc.web.uploads.models.UploadFileInfo;

public interface IUploadFilesService {
    ImageDTO uploadParentProfileImage(ObjectId userId, RequestUploadFile requestUploadFile);
    ImageDTO uploadSonProfileImage(ObjectId userId, RequestUploadFile requestUploadFile);
    ImageDTO uploadParentProfileImageFromUrl(ObjectId userId, String imageUrl);
    UploadFileInfo getProfileImage(String id);
    void deleteProfileImage(String id);
}
