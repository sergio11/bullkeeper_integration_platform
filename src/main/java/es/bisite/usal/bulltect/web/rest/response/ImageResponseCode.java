package es.bisite.usal.bulltect.web.rest.response;

public enum ImageResponseCode implements IResponseCodeTypes {

	IMAGE_UPLOAD_SUCCESSFULLY(1000L), FAILED_TO_UPLOAD_IMAGE(1001L), 
        IMAGE_DELETED_SUCCESSFULLY(1002L), UPLOAD_FILE_IS_TOO_LARGE(1003L);

    private Long code;

    private ImageResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
