package es.bisite.usal.bulltect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ImageUploadMapper {

    @Named("uploadFileInfoToImageDTO")
    public abstract ImageDTO uploadFileInfoToImageDTO(UploadFileInfo uploadFileInfo);

    @IterableMapping(qualifiedByName = "uploadFileInfoToImageDTO")
    public abstract Iterable<ImageDTO> uploadFileInfoToImageDTO(Iterable<UploadFileInfo> uploadFileInfoList);
}
