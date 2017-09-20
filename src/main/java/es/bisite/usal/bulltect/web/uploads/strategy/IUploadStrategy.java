package es.bisite.usal.bulltect.web.uploads.strategy;

import es.bisite.usal.bulltect.web.uploads.exceptions.FileNotFoundException;
import es.bisite.usal.bulltect.web.uploads.exceptions.UploadFailException;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;

public interface IUploadStrategy<T, E extends RequestUploadFile> {
	T save(E fileinfo) throws UploadFailException;
	void delete(T id);
	UploadFileInfo get(T id) throws FileNotFoundException, UploadFailException;
}
