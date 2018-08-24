package sanchez.sanchez.sergio.masoc.web.uploads.strategy;

import sanchez.sanchez.sergio.masoc.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.masoc.web.uploads.models.UploadFileInfo;

public interface IUploadStrategy<T, E extends RequestUploadFile> {

    T save(E fileinfo);

    void delete(T id);

    UploadFileInfo get(T id);
    
    Boolean exists(T id);
}
