package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;

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
