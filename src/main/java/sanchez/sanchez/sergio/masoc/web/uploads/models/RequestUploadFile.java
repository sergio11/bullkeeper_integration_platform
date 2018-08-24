package sanchez.sanchez.sergio.masoc.web.uploads.models;

import java.io.Serializable;

public class RequestUploadFile implements Serializable {
	
	public final static String ORIGINAL_NAME = "originalName";
	public final static String CONTENT_TYPE = "contentType";
	
	private static final long serialVersionUID = 1L;
	
	private byte[] bytes;
    private String contentType;
    private String originalName;

    public RequestUploadFile(byte[] bytes, String contentType, String originalName) {
        this.bytes = bytes;
        this.contentType = contentType;
        this.originalName = originalName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Override
    public String toString() {
        return "RequestUploadFile{" + "bytes=" + bytes + ", contentType=" + contentType + ", originalName=" + originalName + '}';
    }

}
