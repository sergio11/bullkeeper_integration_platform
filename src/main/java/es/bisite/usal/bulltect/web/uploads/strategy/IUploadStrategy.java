package es.bisite.usal.bulltect.web.uploads.strategy;

import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;

public interface IUploadStrategy<T, E extends RequestUploadFile> {

    T save(E fileinfo);

    void delete(T id);

    UploadFileInfo get(T id);
    
    Boolean exists(T id);
}
