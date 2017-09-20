package es.bisite.usal.bulltect.web.dto.response;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageDTO extends ResourceSupport {

    @JsonProperty("id")
    private String identity;
    @JsonProperty("size")
    private Long size;
    @JsonProperty("content-type")
    private String contentType;

    public ImageDTO() {
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String id) {
        this.identity = id;
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
}
