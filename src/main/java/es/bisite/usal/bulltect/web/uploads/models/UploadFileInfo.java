package es.bisite.usal.bulltect.web.uploads.models;

import java.io.Serializable;

public class UploadFileInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long size;
    private String contentType;
    private byte[] content;

    public UploadFileInfo(Long size, String contentType, byte[] content) {
        this.size = size;
        this.contentType = contentType;
        this.content = content;
    }
    
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
